package com.bartczak.zai.lodging.hotel;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Builder
public class AddHotelRequest {
    @NotNull
    private final int maxGuests;
    @NotNull
    private final BigDecimal pricePerNight;
}
