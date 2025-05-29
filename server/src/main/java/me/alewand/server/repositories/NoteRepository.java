package me.alewand.server.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import me.alewand.server.models.Note;

public interface NoteRepository extends JpaRepository<Note, UUID> {
}
