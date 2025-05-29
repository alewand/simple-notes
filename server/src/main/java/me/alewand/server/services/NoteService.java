package me.alewand.server.services;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import me.alewand.server.errors.NoteToDeleteDoesntBelongToUserException;
import me.alewand.server.errors.NoteToDeleteNotFoundException;
import me.alewand.server.errors.NoteToUpdateDoesntBelongToUserException;
import me.alewand.server.errors.NoteToUpdateNotFoundException;
import me.alewand.server.models.Note;
import me.alewand.server.models.User;
import me.alewand.server.repositories.NoteRepository;

public class NoteService {

    private final NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    /**
     * Retrieves all notes for a given user.
     *
     * @param user the user whose notes are to be retrieved
     * @return a list of notes belonging to the user, or an empty list if none exist
     */
    public List<Note> getNotes(User user) {
        return noteRepository.findAllByUser(user)
                .orElse(Collections.emptyList());
    }

    /**
     * Retrieves a specific note by its ID for a given user.
     *
     * @param noteId the ID of the note to retrieve
     * @param user   the user who owns the note
     * @return the note if found, or null if not found
     */
    public void createNote(String title, String content, User user) {
        var note = new Note(title, content, user);
        noteRepository.save(note);
    }

    /**
     * Updates an existing note for a given user.
     * 
     * @param noteId  the ID of the note to update
     * @param title   the new title for the note
     * @param content the new content for the note
     * @param user    the user who owns the note
     * @param service the service name for logging or error handling
     * @throws NoteToUpdateNotFoundException           if the note does not exist
     * @throws NoteToUpdateDoesntBelongToUserException if the note does not belong
     */
    public void updateNote(UUID noteId, String title, String content, User user, String service) {
        var note = noteRepository.findById(noteId)
                .orElseThrow(() -> new NoteToUpdateNotFoundException(Map.of("service", service, "requestedNoteId",
                        noteId.toString(), "nickname", user.getNickname())));

        if (!note.getUser().getUserId().equals(user.getUserId())) {
            throw new NoteToUpdateDoesntBelongToUserException(Map.of("service", service, "requestedNoteId",
                    noteId.toString(), "noteOwnerNickname", note.getUser().getNickname(), "nickname",
                    user.getNickname()));
        }

        note.setTitle(title);
        note.setContent(content);
        noteRepository.save(note);
    }

    /**
     * Deletes a specific note by its ID for a given user.
     *
     * @param noteId  the ID of the note to delete
     * @param user    the user who owns the note
     * @param service the service name for logging or error handling
     * @throws NoteToDeleteNotFoundException           if the note does not exist
     * @throws NoteToDeleteDoesntBelongToUserException if the note does not belong
     *                                                 to the user
     */
    public void deleteNote(UUID noteId, User user, String service) {
        var note = noteRepository.findById(noteId)
                .orElseThrow(() -> new NoteToDeleteNotFoundException(Map.of("service", service, "requestedNoteId",
                        noteId.toString(), "nickname", user.getNickname())));

        if (!note.getUser().getUserId().equals(user.getUserId())) {
            throw new NoteToDeleteDoesntBelongToUserException(Map.of("service", service, "requestedNoteId",
                    noteId.toString(), "noteOwnerNickname", note.getUser().getNickname(), "nickname",
                    user.getNickname()));
        }

        noteRepository.delete(note);
    }
}
