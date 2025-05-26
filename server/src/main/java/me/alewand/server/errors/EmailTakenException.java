package me.alewand.server.errors;

import java.util.Map;

public class EmailTakenException extends ApiException {

    public EmailTakenException(Map<String, String> metadata) {
        super("Użytkownik o takim adresie email już istnieje.", 409, metadata,
                "Operacja nie powiodła się - adres email jest już zajęty.");

    }
}
