package com.bartczak.zai.lodging.hotel;

import com.bartczak.zai.lodging.booking.Booking;
import com.bartczak.zai.lodging.booking.BookingDetails;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class HotelService {

    private final HotelRepository hotelRepository;

    public HotelPageResponse getPage(HotelPagesRequest hotelPagesRequest) {
        val pageRequest = PageRequest.of(hotelPagesRequest.getPageNumber(), hotelPagesRequest.getPageSize());

        val hotelPage = hotelRepository.findAll(pageRequest);
        return HotelPageResponse.builder()
                .totalPages(hotelPage.getTotalPages())
                .totalItems(hotelPage.getTotalElements())
                .hotels(hotelPage.toList())
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
}
