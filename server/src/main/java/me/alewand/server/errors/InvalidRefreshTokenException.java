package me.alewand.server.errors;

import java.util.Map;

public class InvalidRefreshTokenException extends ApiException {

    public InvalidRefreshTokenException() {
        super("Twoja sesja wygasła. Zaloguj się ponownie.", 401, Map.of("service", "refresh-token"),
                "Weryfikacja tokenu odświeżającego nie powiodła się - token jest nieprawidłowy.");
    }

}
