package com.bartczak.zai.lodging.booking;

import com.bartczak.zai.lodging.booking.dto.CreateBookingRequest;
import com.bartczak.zai.lodging.booking.entity.Booking;
import com.bartczak.zai.lodging.booking.entity.BookingDetails;
import com.bartczak.zai.lodging.common.InvalidRequestException;
import com.bartczak.zai.lodging.hotel.HotelRepository;
import com.bartczak.zai.lodging.hotel.entity.Hotel;
import com.bartczak.zai.lodging.user.User;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final HotelRepository hotelRepository;

    public void createBooking(Long hotelId, CreateBookingRequest createBookingRequest) {
        val user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        val hotel = hotelRepository
                .findById(hotelId)
                .orElseThrow(IllegalArgumentException::new);

        val requestedBooking = Booking.builder()
                        .bookingDetails(BookingDetails.builder()
                                .startDate(createBookingRequest.getStartDate())
                                .endDate(createBookingRequest.getEndDate())
                                .guestCount(createBookingRequest.getGuestCount())
                                .build())
                        .hotel(hotel)
                        .createdBy(user)
                        .build();

        validateBooking(requestedBooking, hotel);

        bookingRepository.save(requestedBooking);
    }

    private void validateBooking(Booking requestedBooking, Hotel hotel) {
        if (hotel.getMaxGuests() < requestedBooking.getBookingDetails().getGuestCount()) {
            throw new InvalidRequestException("guestCount exceeds hotel capacity");
        }
        for (Booking booking : hotel.getBookings()) {
            if (booking.isDateRangeBetween(requestedBooking)) {
                throw new InvalidRequestException("Booking withing those dates already exists");
            }
        }
    }

    public List<Booking> getBookingsByHotelId(Long id) {
        return bookingRepository.getAllByHotel_Id(id);
    }
}
