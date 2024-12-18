package com.dulfinne.taxi.promocodeservice.exception;

public class EntityAlreadyExistsException extends CustomException {
  public EntityAlreadyExistsException(String messageKey, Object... params) {
    super(messageKey, params);
  }
}
