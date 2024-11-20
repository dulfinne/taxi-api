package com.dulfinne.taxi.passengerservice.exception;

import com.dulfinne.taxi.passengerservice.util.ExceptionKeys;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

  private final MessageSource validationMessageSource;
  private final MessageSource exceptionMessageSource;

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException ex) {
    String message =
        exceptionMessageSource.getMessage(
            ex.getMessageKey(), ex.getParams(), LocaleContextHolder.getLocale());
    ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND, message);
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
  }

  @ExceptionHandler(EntityAlreadyExistsException.class)
  public ResponseEntity<ErrorResponse> handleEntityAlreadyExistsException(
      EntityAlreadyExistsException ex) {

    String message =
        exceptionMessageSource.getMessage(
            ex.getMessageKey(), ex.getParams(), LocaleContextHolder.getLocale());
    ErrorResponse errorResponse = new ErrorResponse(HttpStatus.CONFLICT, message);
    return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, List<String>>> handleValidationExceptions(
      MethodArgumentNotValidException ex) {
    Map<String, List<String>> errors = new HashMap<>();

    for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
      String fieldName = fieldError.getField();
      String errorMessage = fieldError.getDefaultMessage();

      String message =
          validationMessageSource.getMessage(errorMessage, null, LocaleContextHolder.getLocale());

      errors.computeIfAbsent(fieldName, k -> new ArrayList<>()).add(message);
    }
    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(HandlerMethodValidationException.class)
  public ResponseEntity<Map<String, String>> handleHandlerMethodValidationException(
      HandlerMethodValidationException ex) {
    Map<String, String> errors = new HashMap<>();

    ex.getAllErrors()
        .forEach(
            error -> {
              String fieldName = error.getCodes()[0];
              String[] parts = fieldName.split("\\.");
              fieldName = parts[parts.length - 1];

              String errorMessage = error.getDefaultMessage();
              errors.put(fieldName, errorMessage);
            });

    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex) {
    String message =
        exceptionMessageSource.getMessage(
            ExceptionKeys.UNKNOWN_ERROR, null, LocaleContextHolder.getLocale());

    ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, message);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
  }
}
