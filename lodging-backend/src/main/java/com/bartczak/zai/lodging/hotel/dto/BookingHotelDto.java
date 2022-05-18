package com.bartczak.zai.lodging.hotel.dto;

import com.bartczak.zai.lodging.hotel.entity.Address;
import com.bartczak.zai.lodging.hotel.entity.Hotel;
import lombok.Data;

import javax.validation.constraints.NotNull;


@Data
public class BookingHotelDto {
    @NotNull
    private final String name;
    @NotNull
    private final Address address;

    public static BookingHotelDto from(Hotel hotel) {
        return new BookingHotelDto(hotel.getName(), hotel.getAddress());
    }
}
