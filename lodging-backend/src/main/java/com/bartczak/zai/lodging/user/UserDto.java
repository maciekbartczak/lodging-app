package com.bartczak.zai.lodging.user;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserDto {
    private final Long id;
    private final String username;
    private final String firstName;
    private final String lastName;
    private final List<String> roles;
}
