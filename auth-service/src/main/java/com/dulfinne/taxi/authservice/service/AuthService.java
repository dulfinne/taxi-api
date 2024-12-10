package com.dulfinne.taxi.authservice.service;

import com.dulfinne.taxi.authservice.dto.request.LoginRequest;
import com.dulfinne.taxi.authservice.dto.request.RefreshTokenRequest;
import com.dulfinne.taxi.authservice.dto.request.RegistrationRequest;
import org.keycloak.representations.AccessTokenResponse;

public interface AuthService {
  void createUser(RegistrationRequest request);

  void createAdmin(RegistrationRequest request);

  AccessTokenResponse login(LoginRequest request);

  AccessTokenResponse refreshToken(RefreshTokenRequest request);
}
