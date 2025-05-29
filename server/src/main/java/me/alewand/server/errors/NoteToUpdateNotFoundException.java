package me.alewand.server.errors;

import java.util.Map;

public class NoteToUpdateNotFoundException extends ApiException {

    public NoteToUpdateNotFoundException(Map<String, String> metadata) {
        super("Nie znaleziono notatki do aktualizacji.", 404, metadata,
                "Operacja nie powiodła się - nie znaleziono notatki do aktualizacji.");
    }

}
