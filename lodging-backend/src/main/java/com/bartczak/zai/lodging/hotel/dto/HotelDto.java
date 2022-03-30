package com.bartczak.zai.lodging.hotel.dto;

import com.bartczak.zai.lodging.booking.Booking;
import com.bartczak.zai.lodging.hotel.entity.Address;
import com.bartczak.zai.lodging.hotel.entity.Hotel;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Set;

@Data
@Builder
public class HotelDto {
    @NotNull
    private final Long id;
    @NotNull
    private final String name;
    @NotNull
    private final BigDecimal pricePerNight;
    @NotNull
    private final int maxGuests;
    @NotNull
    private final Set<Booking> bookings;
    @NotNull
    private final Address address;
    @NotNull
    private final String imageName;

    public static HotelDto from(Hotel hotel) {
        return HotelDto.builder()
                .id(hotel.getId())
                .name(hotel.getName())
                .pricePerNight(hotel.getPricePerNight())
                .maxGuests(hotel.getMaxGuests())
                .bookings(hotel.getBookings())
                .address(hotel.getAddress())
                .imageName(hotel.getImageName())
                .build();
    }
}
