package com.bartczak.zai.lodging.hotel;

import com.bartczak.zai.lodging.booking.entity.Booking;
import com.bartczak.zai.lodging.booking.entity.BookingDetails;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;


class BookingTest {

    private final Booking first = Booking.builder()
            .bookingDetails(BookingDetails.builder()
                .startDate(LocalDate.of(2022, 3, 22))
                .endDate(LocalDate.of(2022, 3, 25))
                .build())
            .build();

    @ParameterizedTest
    @MethodSource("provideBookings")
    void bookingDateRangesShouldMatch(Booking second) {
        assertTrue(first.isDateRangeBetween(second));
    }

    @Test
    void bookingDateRangesShouldNotMatch() {
        val first = Booking.builder()
                .bookingDetails(BookingDetails.builder()
                    .startDate(LocalDate.of(2022, 3, 22))
                    .endDate(LocalDate.of(2022, 3, 25))
                    .build())
                .build();
        val second = Booking.builder()
                .bookingDetails(BookingDetails.builder()
                    .startDate(LocalDate.of(2022, 3, 19))
                    .endDate(LocalDate.of(2022, 3, 21))
                    .build())
                .build();

        assertFalse(first.isDateRangeBetween(second));
    }

    private static Stream<Arguments> provideBookings() {
        return Stream.of(
                Arguments.of(Booking.builder()
                        .bookingDetails(BookingDetails.builder()
                            .startDate(LocalDate.of(2022, 3, 24))
                            .endDate(LocalDate.of(2022, 3, 25))
                            .build())
                        .build()
                ),
                Arguments.of(Booking.builder()
                        .bookingDetails(BookingDetails.builder()
                            .startDate(LocalDate.of(2022, 3, 23))
                            .endDate(LocalDate.of(2022, 3, 28))
                            .build())
                        .build()
                ),
                Arguments.of(Booking.builder()
                        .bookingDetails(BookingDetails.builder()
                            .startDate(LocalDate.of(2022, 3, 19))
                            .endDate(LocalDate.of(2022, 3, 24))
                            .build())
                        .build()
                )
        );
    }
}