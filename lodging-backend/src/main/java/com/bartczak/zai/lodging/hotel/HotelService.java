package com.bartczak.zai.lodging.hotel;

import com.bartczak.zai.lodging.booking.Booking;
import com.bartczak.zai.lodging.booking.BookingDetails;
import com.bartczak.zai.lodging.hotel.dto.*;
import com.bartczak.zai.lodging.hotel.entity.Address;
import com.bartczak.zai.lodging.hotel.entity.Hotel;
import com.bartczak.zai.lodging.user.User;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    public AvailableHotelsResponse getAvailableHotels(AvailableHotelsRequest availableHotelsRequest) {
        val requestedBooking = Booking.builder()
                .bookingDetails(BookingDetails.builder()
                        .startDate(availableHotelsRequest.getBookingDetails().getStartDate())
                        .endDate(availableHotelsRequest.getBookingDetails().getEndDate())
                        .build())
                .build();

        val notBookedHotels = hotelRepository.findByBookingsIsNull();
        val bookedHotels = hotelRepository.findDistinctByBookingsIsNotNull();

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
                })
                .collect(Collectors.toList());

        val availableHotels = Stream.concat(notBookedHotels.stream(), bookingFitsHotels.stream())
                .collect(Collectors.toList());

        return AvailableHotelsResponse.builder()
                .hotels(availableHotels)
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
