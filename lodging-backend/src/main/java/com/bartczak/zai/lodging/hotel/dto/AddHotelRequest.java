package com.bartczak.zai.lodging.hotel.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Builder
public class AddHotelRequest {
    @NotNull
    private final String name;
    @NotNull
    private final AddressDto address;
    @NotNull
    private final int maxGuests;
    @NotNull
    private final BigDecimal pricePerNight;
    private final String image;
}
