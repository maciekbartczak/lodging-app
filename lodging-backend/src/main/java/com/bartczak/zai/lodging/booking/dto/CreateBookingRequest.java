package com.bartczak.zai.lodging.booking.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class CreateBookingRequest {
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final int guestCount;
}
