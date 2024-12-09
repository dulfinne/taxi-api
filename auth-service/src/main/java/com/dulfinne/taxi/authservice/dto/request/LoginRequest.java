package com.dulfinne.taxi.authservice.dto.request;

import com.dulfinne.taxi.authservice.util.ValidationKeys;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
  @NotNull(message = ValidationKeys.USERNAME_REQUIRED)
  private String username;

  @NotNull(message = ValidationKeys.PASSWORD_REQUIRED)
  private String password;
}
