package com.dulfinne.taxi.authservice.dto.request;

import jakarta.validation.constraints.NotEmpty;

public record RefreshTokenRequest(
        @NotEmpty
        String refreshToken
) {
}
