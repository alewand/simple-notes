package me.alewand.server.types.responses;

import lombok.Getter;
import me.alewand.server.models.Note;

@Getter
public class NoteResponse extends CommonResponse {
    private Note note;

    public NoteResponse(Note note) {
        super("Znaleziono notatkę.");
        this.note = note;
    }
}
