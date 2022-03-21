package com.bartczak.zai.lodging.hotel;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class HotelPagesRequest {
    @NotNull
    @Min(0)
    private final int pageNumber;
    @NotNull
    @Min(1)
    private final int pageSize;
}
