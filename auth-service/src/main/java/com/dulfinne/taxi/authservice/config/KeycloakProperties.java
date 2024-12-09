package com.dulfinne.taxi.authservice.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class KeycloakProperties {

  @Value("${app.keycloak.server-url}")
  private String serverUrl;

  @Value("${app.keycloak.client.id}")
  private String clientId;

  @Value("${app.keycloak.realm}")
  private String realm;

  @Value("${app.keycloak.client.secret}")
  private String clientSecret;
}
