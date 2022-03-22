package com.bartczak.zai.lodging.hotel;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@Builder
public class AvailableHotelsRequest {
    @NotNull
    private final LocalDate startDate;
    @NotNull
    private final LocalDate endDate;
}
