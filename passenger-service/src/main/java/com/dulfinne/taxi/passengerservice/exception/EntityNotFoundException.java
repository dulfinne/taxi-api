package com.dulfinne.taxi.passengerservice.exception;

import lombok.Getter;

@Getter
public class EntityNotFoundException extends RuntimeException {

  private final String messageKey;
  private final Object[] params;

  public EntityNotFoundException(String messageKey, Object... params) {
    this.messageKey = messageKey;
    this.params = params;
  }
}
