package com.dulfinne.taxi.rideservice.exception

import com.dulfinne.taxi.rideservice.util.ExceptionKeys
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler(
    private val validationMessageSource: MessageSource,
    private val exceptionMessageSource: MessageSource
) {
    private val log: Logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

    @ExceptionHandler(ClientException::class)
    fun handleClientException(ex: ClientException): ResponseEntity<ErrorResponse> {
        val status = HttpStatus.valueOf(ex.code)
        val errorResponse = ErrorResponse(status, ex.info)

        log.warn("Feign client exception. Handling. Message: ${ex.info}")
        return ResponseEntity.status(status).body(errorResponse)
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMessageNotReadableException(ex: HttpMessageNotReadableException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(HttpStatus.BAD_REQUEST, ex.message ?: "JSON parse error")

        log.warn("HttpMessageNotReadableException. Handling.", ex)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationExceptions(ex: MethodArgumentNotValidException): ResponseEntity<Map<String, String>> {
        val errors = ex.bindingResult.fieldErrors.associate { fieldError ->
            val fieldName = fieldError.field
            val errorMessage = fieldError.defaultMessage ?: "Invalid value"
            fieldName to validationMessageSource.getMessage(errorMessage, null, LocaleContextHolder.getLocale())
        }

        log.warn("Validation exception. Handling. Message: $errors")
        return ResponseEntity(errors, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(EntityNotFoundException::class)
    fun handleEntityNotFoundException(ex: EntityNotFoundException): ResponseEntity<ErrorResponse> {
        val message = exceptionMessageSource.getMessage(
            ex.messageKey, ex.params ?: emptyArray(), LocaleContextHolder.getLocale()
        )
        val errorResponse = ErrorResponse(HttpStatus.NOT_FOUND, message)
        log.info("Entity not found. Handling. Message: $message")
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse)
    }

    @ExceptionHandler(ActionNotAllowedException::class)
    fun handleActionNotAllowedException(ex: ActionNotAllowedException): ResponseEntity<ErrorResponse> {
        val message = exceptionMessageSource.getMessage(
            ex.messageKey, ex.params ?: emptyArray(), LocaleContextHolder.getLocale()
        )
        val errorResponse = ErrorResponse(HttpStatus.CONFLICT, message)

        log.info("Action not allowed. Handling. Message: $message")
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse)
    }

    @ExceptionHandler(ServiceNotAvailableException::class)
    fun handleServiceNotAvailableException(ex: ServiceNotAvailableException): ResponseEntity<ErrorResponse> {
        val message = exceptionMessageSource.getMessage(
            ex.messageKey, null, LocaleContextHolder.getLocale()
        )
        val errorResponse = ErrorResponse(HttpStatus.SERVICE_UNAVAILABLE, message)
        log.info("Service not available. Handling. Message: $message")
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse)
    }

    @ExceptionHandler(Exception::class)
    fun handleGlobalException(ex: Exception): ResponseEntity<ErrorResponse> {
        val message =
            exceptionMessageSource.getMessage(ExceptionKeys.UNKNOWN_ERROR, null, LocaleContextHolder.getLocale())

        val errorResponse = ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, message)
        log.error("Unknown exception. Handling.", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse)
    }
}