package com.bartczak.zai.lodging.auth;

import lombok.Data;

@Data
public class LoginRequest {
    private final String username;
    private final String password;
}
