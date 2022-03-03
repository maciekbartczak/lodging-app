package com.bartczak.zai.lodging.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@Tag(name = "Auth")
public class AuthController {


    @PostMapping("/register")
    @Operation(description = "registers a new user")
    public ResponseEntity<String> register(@RequestBody @Valid RegisterRequest registerRequest) {
        return ResponseEntity.ok("okok");
    }
}
