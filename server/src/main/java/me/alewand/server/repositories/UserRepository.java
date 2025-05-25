package me.alewand.server.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import me.alewand.server.models.User;

public interface UserRepository extends JpaRepository<User, UUID> {
}
