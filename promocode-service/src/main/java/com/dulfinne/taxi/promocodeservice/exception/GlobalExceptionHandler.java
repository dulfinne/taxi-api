package com.dulfinne.taxi.promocodeservice.exception;

import com.dulfinne.taxi.promocodeservice.util.ExceptionKeys;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
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
  private final MessageSource exceptionMessageSource;

  @ExceptionHandler({ActionNotAllowedException.class, EntityAlreadyExistsException.class})
  public ResponseEntity<ErrorResponse> handleConflictExceptions(CustomException ex) {
    String message =
        exceptionMessageSource.getMessage(
            ex.getMessageKey(), ex.getParams(), LocaleContextHolder.getLocale());
    ErrorResponse errorResponse = new ErrorResponse(HttpStatus.CONFLICT, message);
    return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
  }

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException ex) {
    String message =
        exceptionMessageSource.getMessage(
            ex.getMessageKey(), ex.getParams(), LocaleContextHolder.getLocale());
    ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND, message);
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
  }

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
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<Map<String, String>> handleConstraintViolationException(
      ConstraintViolationException ex) {
    Map<String, String> errors = new HashMap<>();
    for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
      String fieldName = violation.getPropertyPath().toString();
      String errorMessage = violation.getMessage();

      errors.put(fieldName, errorMessage);
    }
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
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
