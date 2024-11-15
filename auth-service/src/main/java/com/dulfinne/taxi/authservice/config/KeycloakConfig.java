package com.dulfinne.taxi.authservice.config;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakConfig {
  @Value("${app.keycloak.server-url}")
  private String serverUrl;

  @Value("${app.keycloak.client.id}")
  private String clientId;

  @Value("${app.keycloak.realm}")
  private String realm;

  @Value("${app.keycloak.admin.username}")
  private String adminUsername;

  @Value("${app.keycloak.admin.password}")
  private String adminPassword;

  @Value("${app.keycloak.client.secret}")
  private String clientSecret;

  @Bean
  public Keycloak keycloak() {
    return KeycloakBuilder.builder()
        .serverUrl(serverUrl)
        .realm(realm)
        .clientId(clientId)
        .clientSecret(clientSecret)
        .username(adminUsername)
        .password(adminPassword)
        .build();
  }

  @Bean
  public RealmResource realmResource(Keycloak keycloak) {
    return keycloak.realm(realm);
  }
}
