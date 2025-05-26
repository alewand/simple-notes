package me.alewand.server.controllers;

import java.util.Map;

import org.jboss.logging.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import me.alewand.server.errors.ApiException;

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
                .body(Map.of("message", ex.getMessage()));
    }

}
