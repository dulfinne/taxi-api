package com.dulfinne.taxi.promocodeservice.exception;

public class EntityNotFoundException extends CustomException {
  public EntityNotFoundException(String messageKey, Object... params) {
    super(messageKey, params);
  }
}
