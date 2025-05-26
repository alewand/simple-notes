package me.alewand.server.errors;

import java.util.Map;

import me.alewand.server.constants.Constants;

public class InvalidRefreshTokenException extends ApiException {

    public InvalidRefreshTokenException() {
        super("Twoja sesja wygasła. Zaloguj się ponownie.", 401, Map.of(Constants.SERVICE_STR, "refresh-token"),
                "Weryfikacja tokenu odświeżającego nie powiodła się - token jest nieprawidłowy.");
    }

}
