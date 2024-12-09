package com.dulfinne.taxi.authservice.service.impl;

import com.dulfinne.taxi.authservice.config.KeycloakProperties;
import com.dulfinne.taxi.authservice.dto.request.LoginRequest;
import com.dulfinne.taxi.authservice.dto.request.RegistrationRequest;
import com.dulfinne.taxi.authservice.model.Role;
import com.dulfinne.taxi.authservice.service.KeycloakService;
import com.dulfinne.taxi.authservice.util.ExceptionKeys;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import javax.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class KeycloakServiceImpl implements KeycloakService {
  private final RealmResource realmResource;
  private final MessageSource exceptionMessageSource;
  private final KeycloakProperties properties;

  public void createUser(RegistrationRequest request) {
    UserRepresentation userRepresentation = setUserRepresentation(request);

    try (Response response = realmResource.users().create(userRepresentation)) {
      checkResponseIsCreated(response);
    }
    String userId = realmResource.users().search(request.getUsername()).get(0).getId();
    UserResource createdUser = realmResource.users().get(userId);

    assignRoleToUser(createdUser, request.getRole());
  }

  public AccessTokenResponse getJwt(LoginRequest request) {
    try (Keycloak userKeycloak =
        KeycloakBuilder.builder()
            .serverUrl(properties.getServerUrl())
            .realm(properties.getRealm())
            .grantType(OAuth2Constants.PASSWORD)
            .clientId(properties.getClientId())
            .clientSecret(properties.getClientSecret())
            .username(request.getUsername())
            .password(request.getPassword())
            .build()) {
      return userKeycloak.tokenManager().getAccessToken();
    }
  }

  private void assignRoleToUser(UserResource user, Role role) {
    RoleRepresentation roleRepresentation =
        realmResource.roles().get(role.toString()).toRepresentation();

    user.roles().realmLevel().add(List.of(roleRepresentation));
  }

  private void checkResponseIsCreated(Response response) {
    int status = response.getStatus();

    if (status != HttpStatus.CREATED.value()) {
      String message = response.getStatusInfo().getReasonPhrase();

      try {
        String responseBody = response.readEntity(String.class);
        JsonNode jsonNode = new ObjectMapper().readTree(responseBody);

        message = jsonNode.path("errorMessage").asText(message);
      } catch (Exception e) {
        message =
            exceptionMessageSource.getMessage(
                ExceptionKeys.KEYCLOAK_RESPONSE_ERROR, null, LocaleContextHolder.getLocale());
      }

      throw new ResponseStatusException(HttpStatus.valueOf(status), message);
    }
  }

  private UserRepresentation setUserRepresentation(RegistrationRequest request) {
    UserRepresentation userRepresentation = new UserRepresentation();

    userRepresentation.setEnabled(true);
    userRepresentation.setUsername(request.getUsername());
    userRepresentation.setEmail(request.getEmail());
    userRepresentation.setEmailVerified(true);

    CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
    credentialRepresentation.setValue(request.getPassword());
    credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
    userRepresentation.setCredentials(List.of(credentialRepresentation));
    return userRepresentation;
  }
}
