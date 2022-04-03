package com.bartczak.zai.lodging.hotel;

import com.bartczak.zai.lodging.booking.Booking;
import com.bartczak.zai.lodging.booking.BookingDetails;
import com.bartczak.zai.lodging.hotel.dto.*;
import com.bartczak.zai.lodging.hotel.entity.Address;
import com.bartczak.zai.lodging.hotel.entity.Hotel;
import com.bartczak.zai.lodging.user.User;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Comparator;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class HotelService {

    private final HotelRepository hotelRepository;
    private final HotelImageService hotelImageService;

    public HotelPageResponse getPage(HotelPagesRequest hotelPagesRequest) {
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

    public HotelPageResponse search(HotelSearchRequest hotelSearchRequest) {
        val bookingDetails = hotelSearchRequest.getBookingDetails();

        val requestedBooking = Booking.builder()
                .bookingDetails(BookingDetails.builder()
                        .startDate(bookingDetails.getStartDate())
                        .endDate(bookingDetails.getEndDate())
                        .build())
                .build();

        val notBookedHotels = hotelRepository.findByBookingsIsNullAndAddress_City(hotelSearchRequest.getCity());
        val bookedHotels = hotelRepository.findDistinctByBookingsIsNotNullAndAddress_City(hotelSearchRequest.getCity());

        // TODO: I don't like this
        val bookingFitsHotels = bookedHotels.stream()
                .filter(hotel -> {
                    val bookings = hotel.getBookings();
                    var fits = true;
                    for (val booking : bookings) {
                        if (booking.isDateRangeBetween(requestedBooking)) {
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
                hotelSearchRequest.getPage().getPageNumber(), hotelSearchRequest.getPage().getPageSize());

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

    public HotelCreatedResponse addHotel(AddHotelRequest addHotelRequest, MultipartFile image) {
        val user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        val imageName = hotelImageService.save(image);
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
}
