package com.dulfinne.taxi.authservice.client.service;

import com.dulfinne.taxi.authservice.dto.request.RefreshTokenRequest;
import org.keycloak.representations.AccessTokenResponse;

public interface ClientService {
  AccessTokenResponse refreshToken(RefreshTokenRequest request);
}
