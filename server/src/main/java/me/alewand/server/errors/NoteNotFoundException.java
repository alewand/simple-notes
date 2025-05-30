package me.alewand.server.errors;

import java.util.Map;

public class NoteNotFoundException extends ApiException {

    public NoteNotFoundException(Map<String, String> metadata) {
        super("Nie znaleziono notatki.", 404, metadata,
                "Operacja nie powiodła się - nie znaleziono notatki do pobrania.");
    }

}
