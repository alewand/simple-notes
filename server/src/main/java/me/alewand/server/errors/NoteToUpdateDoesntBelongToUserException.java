package me.alewand.server.errors;

import java.util.Map;

public class NoteToUpdateDoesntBelongToUserException extends ApiException {

    public NoteToUpdateDoesntBelongToUserException(Map<String, String> metadata) {
        super("Nie możesz zaktualizować notatki, która nie należy do ciebie.", 403,
                metadata,
                "Operacja nie powiodła się - notatka do aktualizacji nie należy do użytkownika.");
    }

}
