package com.bartczak.zai.lodging.hotel;

import com.bartczak.zai.lodging.booking.BookingRepository;
import com.bartczak.zai.lodging.booking.entity.Booking;
import com.bartczak.zai.lodging.booking.entity.BookingDetails;
import com.bartczak.zai.lodging.common.InvalidRequestException;
import com.bartczak.zai.lodging.hotel.dto.*;
import com.bartczak.zai.lodging.hotel.entity.Address;
import com.bartczak.zai.lodging.hotel.entity.Hotel;
import com.bartczak.zai.lodging.user.User;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
@RequiredArgsConstructor
public class HotelService {

    private final HotelRepository hotelRepository;
    private final BookingRepository bookingRepository;
    private final HotelImageService hotelImageService;

    public HotelPageResponse getPage(HotelPagesRequest hotelPagesRequest) {
        if (!hotelPagesRequest.getFilter().getCity().equals("") ||
            hotelPagesRequest.getFilter().getBookingDetails().getStartDate() != null &&
            hotelPagesRequest.getFilter().getBookingDetails().getEndDate() != null) {
                return search(hotelPagesRequest);
        }

        val pageRequest = PageRequest.of(hotelPagesRequest.getPageNumber(), hotelPagesRequest.getPageSize());

        val hotelPage = hotelRepository.findAll(pageRequest);
        val hotels = hotelPage.stream()
                .map(HotelDto::from)
                .toList();

        return HotelPageResponse.builder()
                .totalItems(hotelPage.getTotalElements())
                .hotels(hotels)
                .build();
    }

    private HotelPageResponse search(HotelPagesRequest hotelPagesRequest) {
        val bookingDetails = hotelPagesRequest.getFilter().getBookingDetails();

        val requestedBooking = Booking.builder()
                .bookingDetails(BookingDetails.builder()
                        .startDate(bookingDetails.getStartDate())
                        .endDate(bookingDetails.getEndDate())
                        .build())
                .build();
        Collection<Hotel> notBookedHotels;
        Collection<Hotel> bookedHotels;

        if (!hotelPagesRequest.getFilter().getCity().equals("")) {
            notBookedHotels = hotelRepository.
                    findByBookingsIsNullAndAddress_City(hotelPagesRequest.getFilter().getCity());
            bookedHotels = hotelRepository.
                    findDistinctByBookingsIsNotNullAndAddress_City(hotelPagesRequest.getFilter().getCity());
        } else {
            notBookedHotels = hotelRepository.
                    findByBookingsIsNull();
            bookedHotels = hotelRepository.
                    findDistinctByBookingsIsNotNull();
        }


        // TODO: I don't like this
        val bookingFitsHotels = bookedHotels.stream()
                .filter(hotel -> {
                    val bookings = hotel.getBookings();
                    var fits = true;
                    for (val booking : bookings) {
                        if (requestedBooking.isDateRangeBetween(booking) || booking.isDateRangeBetween(requestedBooking)) {
                            fits = false;
                            break;
                        }
                    }
                    return fits;
                }).toList();

        val availableHotels = Stream.concat(notBookedHotels.stream(), bookingFitsHotels.stream())
                .sorted(Comparator.comparingLong(Hotel::getId))
                .collect(Collectors.toList());

        val pageRequest = PageRequest.of(
                hotelPagesRequest.getPageNumber(), hotelPagesRequest.getPageSize());

        val total = availableHotels.size();
        val start = (int) pageRequest.getOffset();
        val end = Math.min((start + pageRequest.getPageSize()), total);
        final Page<Hotel> page = new PageImpl<>(availableHotels.subList(start, end), pageRequest, total);
        val hotels = page.stream()
                .map(HotelDto::from)
                .toList();

        return HotelPageResponse.builder()
                .totalItems(total)
                .hotels(hotels)
                .build();
    }

    public HotelCreatedResponse addHotel(AddHotelRequest addHotelRequest) {
        val user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        val imageName = hotelImageService.save(addHotelRequest.getImage());
        val hotel = Hotel.builder()
                .name(addHotelRequest.getName())
                .address(Address.of(addHotelRequest.getAddress()))
                .maxGuests(addHotelRequest.getMaxGuests())
                .pricePerNight(addHotelRequest.getPricePerNight())
                .imageName(imageName)
                .bookings(Set.of())
                .createdBy(user)
                .build();
        hotel.getAddress().setHotel(hotel);
        val created = hotelRepository.save(hotel);
        return new HotelCreatedResponse(HotelDto.from(created));
    }

    public HotelDto getHotelById(Long id) {
        return this.hotelRepository
                .findById(id)
                .map(HotelDto::from)
                .orElseThrow(() -> new InvalidRequestException("Hotel with id " + id + " does not exist"));
    }

    public List<HotelDto> getOwnedHotels() {
        val user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return hotelRepository
                .findAllByCreatedBy_Id(user.getId())
                .stream()
                .map(HotelDto::from)
                .toList();
    }

    public List<HotelDto> getAll() {
        return hotelRepository
                .findAll()
                .stream()
                .map(HotelDto::from)
                .toList();
    }

    public ResponseEntity<Void> deleteById(Long id) {
        val user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        val toDelete = hotelRepository
                .findById(id)
                .orElseThrow(() -> new InvalidRequestException("Hotel with id " + id + " does not exist"));
        if (!Objects.equals(toDelete.getCreatedBy().getId(), user.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        bookingRepository.deleteAll(toDelete.getBookings());
        hotelRepository.deleteById(id);

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Void> editHotel(Long id, AddHotelRequest editHotelRequest) {
        val user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        val toEdit = hotelRepository
                .findById(id)
                .orElseThrow(() -> new InvalidRequestException("Hotel with id " + id + " does not exist"));
        if (!Objects.equals(toEdit.getCreatedBy().getId(), user.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        toEdit.setName(editHotelRequest.getName());
        toEdit.getAddress().setStreet(editHotelRequest.getAddress().getStreet());
        toEdit.getAddress().setCity(editHotelRequest.getAddress().getCity());
        toEdit.getAddress().setCountry(editHotelRequest.getAddress().getCountry());
        toEdit.setMaxGuests(editHotelRequest.getMaxGuests());
        toEdit.setPricePerNight(editHotelRequest.getPricePerNight());

        if (editHotelRequest.getImage() != null && !editHotelRequest.getImage().isEmpty()) {
            hotelImageService.delete(toEdit.getImageName());
            val imageName = hotelImageService.save(editHotelRequest.getImage());
            toEdit.setImageName(imageName);
        }

        hotelRepository.save(toEdit);
        return ResponseEntity.ok().build();
    }
}
