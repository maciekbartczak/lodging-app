package com.bartczak.zai.lodging.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginRequest {
    private final String username;
    private final String password;
}
