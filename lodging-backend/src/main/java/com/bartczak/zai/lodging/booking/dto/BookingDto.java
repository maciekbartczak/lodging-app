package com.bartczak.zai.lodging.booking.dto;

import com.bartczak.zai.lodging.booking.entity.BookingDetails;
import com.bartczak.zai.lodging.hotel.dto.BookingHotelDto;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class BookingDto {
    @NotNull
    private Long id;
    @NotNull
    private BookingDetails bookingDetails;
    @NotNull
    private BookingHotelDto hotel;
}
