package com.dulfinne.taxi.authservice.controller.api;

import com.dulfinne.taxi.authservice.dto.request.LoginRequest;
import com.dulfinne.taxi.authservice.dto.request.RefreshTokenRequest;
import com.dulfinne.taxi.authservice.dto.request.RegistrationRequest;
import com.dulfinne.taxi.authservice.exception.ErrorResponse;
import jakarta.validation.Valid;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Tag(name = "Auth Controller", description = "Work with authentication")
public interface AuthApi {

  @Operation(
      operationId = "registerUser",
      summary = "Register user for not admin roles",
      responses = {
        @ApiResponse(responseCode = "201"),
        @ApiResponse(
            responseCode = "403",
            description = "Attempt to create admin",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      })
  @PostMapping("/register")
  ResponseEntity<Void> registerUser(@RequestBody @Valid RegistrationRequest request);

  @Operation(
      operationId = "registerAdmin",
      summary = "Register admin",
      responses = {@ApiResponse(responseCode = "201")})
  @PostMapping("/admin/register")
  ResponseEntity<Void> registerAdmin(@RequestBody @Valid RegistrationRequest request);

  @Operation(
      operationId = "login",
      summary = "Login and get jwt's",
      responses = {
        @ApiResponse(
            responseCode = "200",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AccessTokenResponse.class))),
      })
  @PostMapping("/login")
  ResponseEntity<AccessTokenResponse> login(@RequestBody @Valid LoginRequest loginRequest);

  @Operation(
      operationId = "refreshToken",
      summary = "Refreshes your token",
      responses = {
        @ApiResponse(
            responseCode = "200",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AccessTokenResponse.class))),
      })
  @PostMapping("/refresh-token")
  ResponseEntity<AccessTokenResponse> refreshToken(@RequestBody @Valid RefreshTokenRequest request);
}
