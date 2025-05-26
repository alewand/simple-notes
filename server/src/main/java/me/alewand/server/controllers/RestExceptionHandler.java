package me.alewand.server.controllers;

import java.util.Map;

import org.jboss.logging.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import me.alewand.server.constants.Constants;
import me.alewand.server.errors.ApiException;

/**
 * Global exception handler for REST controllers.
 * Handles ApiException and ConstraintViolationException.
 */
@RestControllerAdvice
public class RestExceptionHandler {

    public static final Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class.getName());

    private void putMetaToLogger(Map<String, String> metadata) {
        if (metadata == null || metadata.isEmpty())
            return;

        metadata.forEach(MDC::put);
    }

    private void clearMetaFromLogger() {
        MDC.clear();
    }

    private void logError(ApiException ex) {
        putMetaToLogger(ex.getMetadata());
        logger.error(ex.getLoggerMessage());
        clearMetaFromLogger();
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<Map<String, String>> handleApiException(ApiException ex) {
        logError(ex);
        return ResponseEntity.status(ex.getStatusCode())
                .body(Map.of(Constants.MESSAGE_STR, ex.getMessage()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleConstraintViolationException(ConstraintViolationException ex) {
        String message = ex.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .findFirst()
                .orElse("Błąd walidacji danych. Spróbuj ponownie.");
        return ResponseEntity.badRequest()
                .body(Map.of(Constants.MESSAGE_STR,
                        message));
    }

}
