package com.dulfinne.taxi.apigateway.dto;

import org.springframework.http.HttpStatus;

public record FallbackResponse(
    HttpStatus status,
    String message
) {
}
