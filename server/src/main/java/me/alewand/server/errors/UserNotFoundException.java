package me.alewand.server.errors;

import java.util.Map;

public class UserNotFoundException extends ApiException {

    public UserNotFoundException() {
        super("Nie znaleziono użytkownika.", 401,
                "Wyszukiwanie użytkownika nie powiodło się - nie znaleziono użytkownika.");
    }

    public UserNotFoundException(Map<String, String> metadata) {
        super("Nie znaleziono użytkownika.", 401, metadata,
                "Wyszukiwanie użytkownika nie powiodło się - nie znaleziono użytkownika.");
    }

}
