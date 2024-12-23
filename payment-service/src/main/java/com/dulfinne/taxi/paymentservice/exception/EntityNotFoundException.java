package com.dulfinne.taxi.paymentservice.exception;

public class EntityNotFoundException extends CustomException {
  public EntityNotFoundException(String messageKey, Object... params) {
    super(messageKey, params);
  }
}
