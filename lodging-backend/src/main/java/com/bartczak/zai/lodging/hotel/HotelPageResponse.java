package com.bartczak.zai.lodging.hotel;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
public class HotelPageResponse {
    @NotNull
    private final int totalPages;
    @NotNull
    private final long totalItems;
    @NotNull
    private final List<Hotel> hotels;
}
