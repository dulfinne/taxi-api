package com.dulfinne.taxi.authservice.dto.request;

import com.dulfinne.taxi.authservice.model.Role;
import com.dulfinne.taxi.authservice.util.ValidationKeys;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationRequest {

  @NotNull(message = ValidationKeys.USERNAME_REQUIRED)
  @Pattern(regexp = ValidationKeys.USERNAME_FORMAT, message = ValidationKeys.INVALID_USERNAME)
  private String username;

  @NotNull(message = ValidationKeys.PASSWORD_REQUIRED)
  @Pattern(regexp = ValidationKeys.PASSWORD_FORMAT, message = ValidationKeys.INVALID_PASSWORD)
  private String password;

  @NotNull(message = ValidationKeys.EMAIL_REQUIRED)
  @Email(message = ValidationKeys.INVALID_EMAIL)
  private String email;

  @NotNull(message = ValidationKeys.ROLE_REQUIRED)
  private Role role;
}
