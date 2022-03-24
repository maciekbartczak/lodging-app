package com.bartczak.zai.lodging.hotel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class HotelCreatedResponse {
    private final Hotel created;
}

