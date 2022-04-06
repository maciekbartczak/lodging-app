package com.bartczak.zai.lodging.hotel.dto;

import com.bartczak.zai.lodging.hotel.entity.Hotel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class HotelSearchResponse {
    @NotNull
    private final List<HotelDto> hotels;
}

