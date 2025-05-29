package me.alewand.server.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import me.alewand.server.models.User;
import me.alewand.server.services.NoteService;
import me.alewand.server.types.requests.NoteRequest;
import me.alewand.server.types.responses.CommonResponse;
import me.alewand.server.types.responses.NotesResponse;

import java.util.UUID;

import org.jboss.logging.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    public final NoteService noteService;

    private static final Logger logger = LoggerFactory.getLogger(NoteController.class.getName());

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping("/")
    public ResponseEntity<? extends CommonResponse> getNotes(@AuthenticationPrincipal User user) {
        var notes = noteService.getNotes(user);

        if (notes.isEmpty()) {
            return ResponseEntity.status(404).body(new CommonResponse("Nie znaleziono żadnych notatek."));
        }

        return ResponseEntity.ok().body(new NotesResponse(notes));
    }

    @PostMapping("/create")
    public ResponseEntity<CommonResponse> createNote(@Valid @RequestBody NoteRequest noteRequest,
            @AuthenticationPrincipal User user) {
        noteService.createNote(noteRequest.getTitle().trim(), noteRequest.getContent(), user);

        MDC.put("service", "note-create");
        MDC.put("nickname", user.getNickname());
        logger.info("Użytkownik " + user.getNickname() + " utworzył nową notatkę.");
        MDC.clear();

        return ResponseEntity.ok().body(new CommonResponse("Notatka została utworzona."));
    }

    @PostMapping("/update/{noteId}")
    public ResponseEntity<CommonResponse> updateNote(@PathVariable UUID noteId,
            @Valid @RequestBody NoteRequest noteRequest,
            @AuthenticationPrincipal User user) {
        noteService.updateNote(noteId, noteRequest.getTitle().trim(), noteRequest.getContent(), user,
                "note-update");

        MDC.put("service", "note-update");
        MDC.put("nickname", user.getNickname());
        MDC.put("noteId", noteId.toString());
        logger.info("Notatka o ID " + noteId + " została zaktualizowana przez użytkownika " + user.getNickname() + ".");
        MDC.clear();

        return ResponseEntity.ok().body(new CommonResponse("Notatka została zaktualizowana."));
    }

    @DeleteMapping("/delete/{noteId}")
    public ResponseEntity<CommonResponse> deleteNote(@PathVariable UUID noteId,
            @AuthenticationPrincipal User user) {
        noteService.deleteNote(noteId, user, "note-delete");

        MDC.put("service", "note-delete");
        MDC.put("nickname", user.getNickname());
        MDC.put("noteId", noteId.toString());
        logger.info("Notatka o ID " + noteId + " została usunięta przez użytkownika " + user.getNickname() + ".");
        MDC.clear();

        return ResponseEntity.ok().body(new CommonResponse("Notatka została usunięta."));
    }

}
