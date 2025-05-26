package me.alewand.server.errors;

import java.util.Map;

public class InvalidPasswordDuringAuthenticationException extends ApiException {

    public InvalidPasswordDuringAuthenticationException(Map<String, String> metadata) {
        super("Nieprawidłowy login lub hasło.", 401, metadata,
                "Nie udana próba uwierzytelnienia - nieprawidłowe hasło.");
    }

}
