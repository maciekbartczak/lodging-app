package com.bartczak.zai.lodging.booking;

import com.bartczak.zai.lodging.booking.dto.BookingDto;
import com.bartczak.zai.lodging.booking.dto.CreateBookingRequest;
import com.bartczak.zai.lodging.booking.entity.Booking;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/booking")
@Tag(name = "Booking")
public class BookingController {

    private final BookingService bookingService;

    @PostMapping("/{hotelId}/booking")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<Void> createBooking(@PathVariable Long hotelId, @RequestBody CreateBookingRequest createBookingRequest) {
        bookingService.createBooking(hotelId, createBookingRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{hotelId}/booking")
    public List<Booking> getHotelBookings(@PathVariable Long hotelId) {
        return bookingService.getBookingsByHotelId(hotelId);
    }

    @GetMapping("/my")
    @PreAuthorize("hasAuthority('USER')")
    public List<BookingDto> getMyBookings() {
        return bookingService.getMyBookings();
    }

    @DeleteMapping("/{bookingId}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long bookingId) {
        return bookingService.deleteBookingById(bookingId);
    }
}
