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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
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
                                .price(getBookingPrice(createBookingRequest, hotel))
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

    public List<Booking> getMyBookings() {
        val user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return bookingRepository.getAllByCreatedBy_Id(user.getId());
    }

    public ResponseEntity<Void> deleteBookingById(Long id) {
        val user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        val booking = bookingRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);
        if (!Objects.equals(booking.getCreatedBy().getId(), user.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        bookingRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    private BigDecimal getBookingPrice(CreateBookingRequest createBookingRequest, Hotel hotel) {
        val startDate = createBookingRequest.getStartDate();
        val endDate = createBookingRequest.getEndDate();
        val days = ChronoUnit.DAYS.between(startDate, endDate);
        return hotel.getPricePerNight().multiply(BigDecimal.valueOf(days + 1));
    }
}
