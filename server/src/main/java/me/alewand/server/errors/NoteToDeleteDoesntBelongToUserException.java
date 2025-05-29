package me.alewand.server.errors;

import java.util.Map;

public class NoteToDeleteDoesntBelongToUserException extends ApiException {

    public NoteToDeleteDoesntBelongToUserException(Map<String, String> metadata) {
        super(
                "Nie możesz usunąć notatki, która nie należy do Ciebie.",
                403,
                metadata,
                "Operacja nie powiodła się - notatka do usunięcia nie należy do użytkownika.");
    }

}