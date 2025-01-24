package com.dulfinne.taxi.authservice.exception;

import com.dulfinne.taxi.authservice.util.ExceptionKeys;
import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.NotAuthorizedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalExceptionHandler {
  private final MessageSource validationMessageSource;
  private final MessageSource exceptionMessageSource;

  @ExceptionHandler(ResponseStatusException.class)
  public ResponseEntity<ErrorResponse> handleResponseStatusException(ResponseStatusException ex) {
    log.info("Keycloak response exception. Handling. Message: {}", ex.getMessage());
    HttpStatus status = HttpStatus.valueOf(ex.getStatusCode().value());
    ErrorResponse errorResponse = new ErrorResponse(status, ex.getReason());
    return ResponseEntity.status(status).body(errorResponse);
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
    log.warn("Validation exception. Handling. Errors: {}", errors);
    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(InvalidRoleException.class)
  public ResponseEntity<ErrorResponse> handleInvalidRoleException(InvalidRoleException ex) {
    String message =
        exceptionMessageSource.getMessage(
            ex.getMessageKey(), ex.getParams(), LocaleContextHolder.getLocale());
    ErrorResponse errorResponse = new ErrorResponse(HttpStatus.FORBIDDEN, message);
    log.info("Invalid role exception. Handling. Message: {}", message);
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
  }

  @ExceptionHandler(NotAuthorizedException.class)
  public ResponseEntity<ErrorResponse> handleNotAuthorizedException(NotAuthorizedException ex) {
    String message =
        exceptionMessageSource.getMessage(
            ExceptionKeys.KEYCLOAK_UNAUTHORIZED, null, LocaleContextHolder.getLocale());

    ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED, message);
    log.info("Not authorized exception. Handling. Message: {}", message);
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex) {
    log.info("Unknown exception. Handling.", ex);
    String message =
        exceptionMessageSource.getMessage(
            ExceptionKeys.UNKNOWN_ERROR, null, LocaleContextHolder.getLocale());

    ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, message);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
  }
}
