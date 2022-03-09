package com.bartczak.zai.lodging.user;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@RequiredArgsConstructor
public class UserResponse {
    @NotNull
    private final UserDto user;
}
