package com.dulfinne.taxi.passengerservice.exception;

import lombok.Getter;

@Getter
public class EntityAlreadyExistsException extends RuntimeException {

  private final String messageKey;
  private final Object[] params;

  public EntityAlreadyExistsException(String messageKey, Object... params) {
    this.messageKey = messageKey;
    this.params = params;
  }
}
