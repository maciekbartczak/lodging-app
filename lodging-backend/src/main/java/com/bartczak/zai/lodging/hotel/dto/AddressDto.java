package com.bartczak.zai.lodging.hotel.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressDto {
    private final String street;
    private final String city;
    private final String country;
}
