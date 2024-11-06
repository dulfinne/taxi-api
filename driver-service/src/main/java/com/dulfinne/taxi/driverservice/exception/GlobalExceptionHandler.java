package com.dulfinne.taxi.driverservice.exception;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

  private final MessageSource validationMessageSource;

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, String>> handleValidationExceptions(
      MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
      String fieldName = fieldError.getField();
      String errorMessage = fieldError.getDefaultMessage();

      errors.put(
          fieldName,
          validationMessageSource.getMessage(errorMessage, null, LocaleContextHolder.getLocale()));
    }

    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException ex) {
    ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
  }

  @ExceptionHandler(EntityExistsException.class)
  public ResponseEntity<ErrorResponse> handleEntityExistsException(EntityExistsException ex) {
    ErrorResponse errorResponse = new ErrorResponse(HttpStatus.CONFLICT, ex.getMessage());
    return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex) {
    ErrorResponse errorResponse =
            new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unknown error has occurred...");
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
  }
}
