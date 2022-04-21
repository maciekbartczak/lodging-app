package com.bartczak.zai.lodging.booking.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@Builder
public class CreateBookingRequest {
    @NotNull
    private final LocalDate startDate;
    @NotNull
    private final LocalDate endDate;
    @NotNull
    private final int guestCount;
}
