package me.alewand.server.errors;

import java.util.Map;

public class UserNotFoundDuringAuthenticationException extends ApiException {

    public UserNotFoundDuringAuthenticationException(Map<String, String> metadata) {
        super("Nieprawidłowy login lub hasło.", 401, metadata,
                "Nie udana próba uwierzytelnienia - nie znaleziono użytkownika.");
    }

}
