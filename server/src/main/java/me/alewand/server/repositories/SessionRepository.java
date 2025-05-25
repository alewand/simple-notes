package me.alewand.server.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import me.alewand.server.models.Session;
import me.alewand.server.models.User;

public interface SessionRepository extends JpaRepository<Session, UUID> {

    Optional<Session> findByToken(String token);

    Optional<List<Session>> findAllByUser(User user);
}
