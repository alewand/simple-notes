package me.alewand.server.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import me.alewand.server.models.User;

public interface UserRepository extends JpaRepository<User, UUID> {
    boolean existsByNickname(String nickname);

    boolean existsByEmail(String email);

    Optional<User> findByNickname(String nickname);

    Optional<User> findByNicknameOrEmail(String nickname, String email);
}
