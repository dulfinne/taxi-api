package com.dulfinne.taxi.paymentservice.exception;

public class ActionNotAllowedException extends CustomException {
  public ActionNotAllowedException(String messageKey, Object... params) {
    super(messageKey, params);
  }
}
