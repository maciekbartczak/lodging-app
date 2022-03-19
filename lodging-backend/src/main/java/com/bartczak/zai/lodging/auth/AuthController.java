package com.bartczak.zai.lodging.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@Tag(name = "Auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @Operation(description = "registers a new user")
    public ResponseEntity<Void> register(@RequestBody @Valid RegisterRequest registerRequest) {
        authService.registerUser(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    @Operation(description = "logs in a user")
    public ResponseEntity<Void> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        response.addCookie(authService.login(loginRequest));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/logout")
    @Operation(description = "logs a user out")
    public ResponseEntity<Void> logout(HttpServletRequest request, HttpServletResponse response) {
        response.addCookie(authService.logout(request));
        return ResponseEntity.ok().build();
    }
}
