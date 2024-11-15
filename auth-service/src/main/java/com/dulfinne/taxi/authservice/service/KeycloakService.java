package com.dulfinne.taxi.authservice.service;

import com.dulfinne.taxi.authservice.dto.request.LoginRequest;
import com.dulfinne.taxi.authservice.dto.request.RegistrationRequest;
import org.keycloak.representations.AccessTokenResponse;

public interface KeycloakService {
  void createUser(RegistrationRequest request);

  AccessTokenResponse getJwt(LoginRequest request);
}
