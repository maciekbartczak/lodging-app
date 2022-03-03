package com.bartczak.zai.lodging.auth;

import lombok.Data;

@Data
public class RegisterRequest {
    private final String email;
    private final String firstName;
    private final String lastName;
    private final String password;
}
