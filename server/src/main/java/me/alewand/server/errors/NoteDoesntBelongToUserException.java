package me.alewand.server.errors;

import java.util.Map;

public class NoteDoesntBelongToUserException extends ApiException {

    public NoteDoesntBelongToUserException(Map<String, String> metadata) {
        super(
                "Nie możesz wyświetlić notatki, która nie należy do ciebie.",
                403,
                metadata,
                "Operacja nie powiodła się - notatka do pobrania nie należy do użytkownika.");
    }

}