package com.bartczak.zai.lodging.auth;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
public class RegisterRequest {
    @Email
    private final String email;
    @NotBlank
    private final String firstName;
    @NotBlank
    private final String lastName;
    @NotBlank
    @Size(min = 6, max = 50)
    private final String password;
}
