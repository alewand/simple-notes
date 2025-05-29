package me.alewand.server.errors;

import java.util.Map;

import me.alewand.server.constants.Constants;

public class ExpiredRefreshTokenException extends ApiException {

    public ExpiredRefreshTokenException() {
        super("Twoja sesja wygasła. Zaloguj się ponownie.", 401, Map.of("service", "refresh-token"),
                "Weryfikacja tokenu odświeżającego nie powiodła się - token wygasł.");
    }

}
