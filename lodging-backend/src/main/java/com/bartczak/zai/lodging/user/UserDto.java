package com.bartczak.zai.lodging.user;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
public class UserDto {
    @NotNull
    private final Long id;
    @NotNull
    private final String username;
    @NotNull
    private final String firstName;
    @NotNull
    private final String lastName;
    @NotNull
    private final List<String> roles;
}
