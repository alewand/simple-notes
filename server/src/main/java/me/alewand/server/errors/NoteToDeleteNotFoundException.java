package me.alewand.server.errors;

import java.util.Map;

public class NoteToDeleteNotFoundException extends ApiException {

    public NoteToDeleteNotFoundException(Map<String, String> metadata) {
        super("Nie znaleziono notatki do usunięcia.", 404, metadata,
                "Operacja nie powiodła się - nie znaleziono notatki do usunięcia.");
    }

}
