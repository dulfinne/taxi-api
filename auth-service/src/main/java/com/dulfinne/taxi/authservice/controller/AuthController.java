package com.dulfinne.taxi.authservice.controller;

import com.dulfinne.taxi.authservice.dto.request.LoginRequest;
import com.dulfinne.taxi.authservice.dto.request.RegistrationRequest;
import com.dulfinne.taxi.authservice.service.AuthService;
import com.dulfinne.taxi.authservice.service.KeycloakService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;
  private final KeycloakService keycloakService;

  @PostMapping("/register")
  public ResponseEntity<Void> registerUser(@RequestBody @Valid RegistrationRequest request) {
    authService.createUser(request);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @PostMapping("/admin/register")
  public ResponseEntity<Void> registerAdmin(@RequestBody @Valid RegistrationRequest request) {
    authService.createAdmin(request);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @PostMapping("/login")
  public ResponseEntity<AccessTokenResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
    return ResponseEntity.ok(keycloakService.getJwt(loginRequest));
  }
}
