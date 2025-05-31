package me.alewand.server.errors;

import java.util.Map;

public class UserInfoFieldToChangeDoesntExistsException extends ApiException {

    public UserInfoFieldToChangeDoesntExistsException(Map<String, String> metadata) {
        super("Nie możnesz zmienić takich danych dla użytkownika.", 400, metadata,
                "Operacja zmiany pola w danych użytkownika nie powiodła się - takie pole nie istnieje.");

    }
}
