package me.alewand.server.errors;

import java.util.Map;

public class NicknameTakenException extends ApiException {

    public NicknameTakenException(Map<String, String> metadata) {
        super("Użytkownik o takim nicku już istnieje.", 409, metadata,
                "Operacja nie powiodła się - nick jest już zajęty.");

    }
}
