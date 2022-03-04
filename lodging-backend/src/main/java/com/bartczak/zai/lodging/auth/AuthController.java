package com.bartczak.zai.lodging.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@Tag(name = "Auth")
@RequiredArgsConstructor
public class AuthController {

    private final LoginService loginService;
    private final RegisterService registerService;

    @PostMapping("/register")
    @Operation(description = "registers a new user")
    public ResponseEntity<Void> register(@RequestBody @Valid RegisterRequest registerRequest) {
        registerService.registerUser(registerRequest);
        return ResponseEntity.ok(null);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    @Operation(description = "logs in a user")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        return loginService.login(loginRequest);
    }
}
