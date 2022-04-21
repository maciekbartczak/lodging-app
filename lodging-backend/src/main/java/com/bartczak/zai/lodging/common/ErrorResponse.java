package com.bartczak.zai.lodging.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ErrorResponse {
    private final String timestamp;
    private final String message;
}
