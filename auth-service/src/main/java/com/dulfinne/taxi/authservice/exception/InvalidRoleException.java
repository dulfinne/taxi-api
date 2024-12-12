package com.dulfinne.taxi.authservice.exception;

import lombok.Getter;

@Getter
public class InvalidRoleException extends RuntimeException {

  private final String messageKey;
  private final Object[] params;

  public InvalidRoleException(String messageKey, Object... params) {
    this.messageKey = messageKey;
    this.params = params;
  }
}
