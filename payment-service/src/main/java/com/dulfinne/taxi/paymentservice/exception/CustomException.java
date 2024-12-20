package com.dulfinne.taxi.paymentservice.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public abstract class CustomException extends RuntimeException {
  private final String messageKey;
  private final Object[] params;
}
