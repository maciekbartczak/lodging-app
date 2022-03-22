package com.bartczak.zai.lodging.hotel;

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
public class AvailableHotelsResponse {
    @NotNull
    private final List<Hotel> hotels;
}

