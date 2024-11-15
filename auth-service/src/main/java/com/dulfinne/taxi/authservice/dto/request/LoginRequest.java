package com.dulfinne.taxi.authservice.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
  @NotNull(message = "Username can't be empty")
  private String username;

  @NotNull(message = "Password can't be empty")
  private String password;
}
