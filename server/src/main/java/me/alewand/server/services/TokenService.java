package me.alewand.server.services;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

import javax.crypto.SecretKey;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import me.alewand.server.errors.ApiError;
import me.alewand.server.models.Session;
import me.alewand.server.models.User;
import me.alewand.server.repositories.SessionRepository;
import me.alewand.server.repositories.UserRepository;

/**
 * Service for handling token generation and validation.
 */
@Service
public class TokenService {

    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;

    @Value("${jwt.secret}")
    private String secret;

    public TokenService(UserRepository userRepository, SessionRepository sessionRepository) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
    }

    /**
     * Generates a JWT access token for the given user.
     *
     * @param user The user for whom the access token is generated.
     * @return A JWT access token as a String.
     */
    public String generateAccessToken(User user) {
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        Instant now = Instant.now();
        Instant expiresAt = now.plus(15, ChronoUnit.MINUTES);

        return Jwts.builder()
                .subject(user.getUserId().toString())
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiresAt))
                .claim("nickname", user.getNickname())
                .claim("role", user.getRole().name())
                .signWith(key)
                .compact();
    }

    /**
     * Generates a refresh token and creates session for the given user.
     *
     * @param user The user for whom the refresh token is generated.
     * @return A refresh token as a String.
     * @throws ApiError if there is an error during token generation or session
     *                  creation.
     */
    public String generateRefreshToken(User user) {
        var refreshToken = UUID.randomUUID().toString();
        var hashedToken = DigestUtils.sha256Hex(refreshToken);
        Instant expiresAt = Instant.now().plus(7, ChronoUnit.DAYS);

        Session sesion = new Session(hashedToken, LocalDateTime.from(expiresAt), user);

        sessionRepository.save(sesion);

        return refreshToken;
    }

    /**
     * Refreshes (generates new) the access token using the provided refresh token.
     *
     * @param refreshToken The refresh token to validate and use for generating a
     *                     new access token.
     * @return A new access token as a String.
     * @throws ApiError if the refresh token is invalid or expired.
     */
    public String refreshAccessToken(String refreshToken) {
        var hashedRefreshToken = DigestUtils.sha256Hex(refreshToken);
        Session session = sessionRepository.findByToken(hashedRefreshToken)
                .orElseThrow(() -> new ApiError(401, "Twój token odświeżający jest nieprawidłowy."));

        if (session.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new ApiError(401, "Twoja sesja wygasła. Zaloguj się ponownie.");
        }

        User user = userRepository.findById(session.getUser().getUserId())
                .orElseThrow(() -> new ApiError(404, "Nie znaleziono użytkownika dla sesji."));

        return generateAccessToken(user);
    }

    /**
     * Revokes the refresh token by deleting the associated session.
     *
     * @param refreshToken The refresh token to revoke.
     * @throws ApiError if the session for the provided token is not found.
     */
    public void revokeRefreshToken(String refreshToken) {
        var hashedRefreshToken = DigestUtils.sha256Hex(refreshToken);
        Session session = sessionRepository.findByToken(hashedRefreshToken)
                .orElseThrow(() -> new ApiError(404, "Nie znaleziono sesji dla podanego tokena."));

        sessionRepository.delete(session);
    }

    /**
     * Revokes all refresh tokens for a given user by deleting all sessions
     * associated with that user.
     * 
     * @param user The user whose refresh tokens are to be revoked.
     */
    public void revokeAllRefrshTokens(User user) {
        var sessions = sessionRepository.findAllByUser(user)
                .orElseThrow(() -> new ApiError(404, "Nie znaleziono sesji dla użytkownika."));

        sessionRepository.deleteAll(sessions);
    }

}
