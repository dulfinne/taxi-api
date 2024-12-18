package com.dulfinne.taxi.promocodeservice.exception;

import lombok.Getter;

@Getter
public class ActionNotAllowedException extends RuntimeException {
  private final String messageKey;
  private final Object[] params;

  public ActionNotAllowedException(String messageKey, Object... params) {
    this.messageKey = messageKey;
    this.params = params;
  }
}
