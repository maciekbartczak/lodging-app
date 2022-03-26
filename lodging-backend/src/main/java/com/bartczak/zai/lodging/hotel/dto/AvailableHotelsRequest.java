package com.bartczak.zai.lodging.hotel.dto;

import com.bartczak.zai.lodging.booking.BookingDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class AvailableHotelsRequest {
    @NotNull
    private final BookingDetails bookingDetails;
}
