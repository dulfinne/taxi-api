package com.dulfinne.taxi.authservice.client.service.impl;

import com.dulfinne.taxi.authservice.client.KeycloakClient;
import com.dulfinne.taxi.authservice.client.service.ClientService;
import com.dulfinne.taxi.authservice.config.KeycloakProperties;
import com.dulfinne.taxi.authservice.dto.request.RefreshTokenRequest;
import lombok.RequiredArgsConstructor;
import org.keycloak.OAuth2Constants;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
  private final KeycloakClient keycloakClient;
  private final KeycloakProperties properties;

  @Override
  public AccessTokenResponse refreshToken(RefreshTokenRequest request) {
    String token = request.refreshToken();

    Map<String, String> clientRequest = new HashMap<>();
    clientRequest.put(OAuth2Constants.GRANT_TYPE, OAuth2Constants.REFRESH_TOKEN);
    clientRequest.put(OAuth2Constants.CLIENT_ID, properties.getClientId());
    clientRequest.put(OAuth2Constants.CLIENT_SECRET, properties.getClientSecret());
    clientRequest.put(OAuth2Constants.REFRESH_TOKEN, token);

    return keycloakClient.refreshToken(clientRequest);
  }
}
