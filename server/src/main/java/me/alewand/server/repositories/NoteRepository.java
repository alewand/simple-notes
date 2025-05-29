package me.alewand.server.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import me.alewand.server.models.Note;
import me.alewand.server.models.User;

public interface NoteRepository extends JpaRepository<Note, UUID> {
    Optional<List<Note>> findAllByUser(User user);
}
