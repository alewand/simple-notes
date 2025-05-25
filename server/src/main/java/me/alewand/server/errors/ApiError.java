package me.alewand.server.errors;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

@Getter
public class ApiError extends RuntimeException {
    private final int statusCode;
    private final Map<String, String> metadata;

    public ApiError(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
        this.metadata = new HashMap<>();
    }

    public ApiError(int statusCode, String message, Map<String, String> metadata) {
        super(message);
        this.statusCode = statusCode;
        this.metadata = metadata != null ? metadata : new HashMap<>();
    }

}
