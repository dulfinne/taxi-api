package com.dulfinne.taxi.passengerservice.exception;

import lombok.Getter;

@Getter
public class IllegalSortFieldException extends RuntimeException {
  private final String messageKey;
  private final Object[] params;

  public IllegalSortFieldException(String messageKey, Object... params) {
    this.messageKey = messageKey;
    this.params = params;
  }
}
