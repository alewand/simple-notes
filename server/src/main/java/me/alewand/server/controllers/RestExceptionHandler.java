package me.alewand.server.controllers;

import java.util.Map;

import org.jboss.logging.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MissingRequestCookieException;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import me.alewand.server.errors.ApiException;
import me.alewand.server.errors.ExpiredRefreshTokenException;
import me.alewand.server.errors.InvalidRefreshTokenException;
import me.alewand.server.errors.RefreshTokenNotFoundException;
import me.alewand.server.services.TokenService;
import me.alewand.server.types.responses.CommonResponse;

/**
 * Global exception handler for REST controllers.
 * Handles ApiException and ConstraintViolationException.
 */
@RestControllerAdvice
public class RestExceptionHandler {

    public static final Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class.getName());
    private final TokenService tokenService;

    public RestExceptionHandler(TokenService tokenService) {
        this.tokenService = tokenService;
    }

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
    public ResponseEntity<CommonResponse> handleApiException(ApiException ex) {
        logError(ex);
        return ResponseEntity.status(ex.getStatusCode())
                .body(new CommonResponse(ex.getMessage()));
    }

    @ExceptionHandler({ InvalidRefreshTokenException.class, ExpiredRefreshTokenException.class,
            RefreshTokenNotFoundException.class })
    public ResponseEntity<CommonResponse> handleRefreshTokenException(ApiException ex) {
        logError(ex);
        ResponseCookie clearedCookie = tokenService.clearRefreshTokenCookie();
        return ResponseEntity.status(ex.getStatusCode())
                .header("Set-Cookie", clearedCookie.toString())
                .body(new CommonResponse(ex.getMessage()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<CommonResponse> handleConstraintViolationException(ConstraintViolationException ex) {
        String message = ex.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .findFirst()
                .orElse("Błąd walidacji danych. Spróbuj ponownie.");
        return ResponseEntity.badRequest()
                .body(new CommonResponse(message));
    }

    @ExceptionHandler(MissingRequestCookieException.class)
    public ResponseEntity<CommonResponse> handleMissingRequestCookieException(MissingRequestCookieException ex) {
        return ResponseEntity.badRequest().body(new CommonResponse("Brak tokena odświeżającego. Spróbuj ponownie."));
    }

}
