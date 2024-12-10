package com.dulfinne.taxi.authservice.service.impl;

import com.dulfinne.taxi.authservice.client.service.ClientService;
import com.dulfinne.taxi.authservice.dto.request.LoginRequest;
import com.dulfinne.taxi.authservice.dto.request.RefreshTokenRequest;
import com.dulfinne.taxi.authservice.dto.request.RegistrationRequest;
import com.dulfinne.taxi.authservice.exception.InvalidRoleException;
import com.dulfinne.taxi.authservice.model.Role;
import com.dulfinne.taxi.authservice.service.AuthService;
import com.dulfinne.taxi.authservice.service.KeycloakService;
import com.dulfinne.taxi.authservice.util.ExceptionKeys;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final KeycloakService keycloakService;
  private final ClientService clientService;

  public void createUser(RegistrationRequest request) {
    if (request.getRole() != Role.ROLE_ADMIN) {
      keycloakService.createUser(request);
    } else {
      throw new InvalidRoleException(ExceptionKeys.NOT_ALLOWED_CREATE_ROLE, request.getRole());
    }
  }

  public void createAdmin(RegistrationRequest request) {
    keycloakService.createUser(request);
  }

  public AccessTokenResponse login(LoginRequest request) {
    return keycloakService.getJwt(request);
  }

  public AccessTokenResponse refreshToken(RefreshTokenRequest request) {
    return clientService.refreshToken(request);
  }
}
