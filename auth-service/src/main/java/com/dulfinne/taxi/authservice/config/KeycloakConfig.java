package com.dulfinne.taxi.authservice.config;

import lombok.RequiredArgsConstructor;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class KeycloakConfig {

  private final KeycloakProperties properties;

  @Bean
  public Keycloak keycloak() {
    return KeycloakBuilder.builder()
        .serverUrl(properties.getServerUrl())
        .realm(properties.getRealm())
        .clientId(properties.getClientId())
        .clientSecret(properties.getClientSecret())
        .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
        .build();
  }

  @Bean
  public RealmResource realmResource(Keycloak keycloak) {
    return keycloak.realm(properties.getRealm());
  }
}
