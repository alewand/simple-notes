package me.alewand.server.types.responses;

import java.util.List;

import lombok.Getter;
import me.alewand.server.models.Note;

@Getter
public class NotesResponse extends CommonResponse {

    private List<Note> notes;

    public NotesResponse(List<Note> notes) {
        super("Znaleziono notatki. Liczba: " + notes.size());
        this.notes = notes;
    }
}
