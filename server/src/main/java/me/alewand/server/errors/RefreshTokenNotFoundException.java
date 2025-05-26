package me.alewand.server.errors;

import java.util.Map;

public class RefreshTokenNotFoundException extends ApiException {

    public RefreshTokenNotFoundException(Map<String, String> metadata) {
        super("Nie znaleziono żadnych sesji.", 401, metadata,
                "Operacja nie powiodła się - nie znaleziono tokenu lub tokenów odświeżających.");
    }

}
