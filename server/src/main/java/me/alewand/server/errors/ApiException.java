package me.alewand.server.errors;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {
    private final int statusCode;
    private final Map<String, String> metadata;
    private final String loggerMessage;

    public ApiException(String message, int statusCode, Map<String, String> metadata, String loggerMessage) {
        super(message);
        this.statusCode = statusCode;
        this.metadata = metadata != null ? metadata : new HashMap<>();
        this.loggerMessage = loggerMessage;
    }

    public ApiException(String message, int statusCode) {
        this(message, statusCode, null, null);
    }

    public ApiException(String message, int statusCode, String loggerMessage) {
        this(message, statusCode, null, loggerMessage);
    }

    public ApiException(String message, int statusCode, Map<String, String> metadata) {
        this(message, statusCode, metadata, null);
    }

}
