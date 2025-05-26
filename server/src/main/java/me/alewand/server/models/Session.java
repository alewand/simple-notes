package me.alewand.server.models;

import java.time.Instant;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sessions")
public class Session {

    public Session(String token, Instant expiresAt, User user) {
        this.token = token;
        this.expiresAt = expiresAt;
        this.user = user;
    }

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID sessionId;

    @Column(nullable = false)
    private String token;

    @Column(nullable = false)
    private Instant expiresAt;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant startedAt;
}
