package me.alewand.server.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import me.alewand.server.models.User;
import me.alewand.server.services.AuthService;
import me.alewand.server.services.TokenService;
import me.alewand.server.types.requests.LoginRequest;
import me.alewand.server.types.requests.RegisterRequest;
import me.alewand.server.types.responses.LoginResponse;

import org.jboss.logging.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final TokenService tokenService;

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class.getName());

    public AuthController(AuthService authService, TokenService tokenService) {
        this.authService = authService;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        var nicknameOrEmail = request.getNicknameOrEmail();
        var password = request.getPassword();
        User user = authService.getAuthenticatedUser(nicknameOrEmail, password, "login");
        var accessToken = tokenService.generateAccessToken(user);
        var refreshToken = tokenService.generateRefreshToken(user);
        ResponseCookie refreshTokenCookie = tokenService.generateRefreshTokenCookie(refreshToken);

        MDC.put("service", "login");
        MDC.put("nicknameOrEmail", nicknameOrEmail);
        logger.info("Użytkownik " + user.getNickname() + " zalogował się pomyślnie.");
        MDC.clear();

        return ResponseEntity.ok()
                .header("Set-Cookie", refreshTokenCookie.toString())
                .body(new LoginResponse("Zalogowano pomyślnie.", accessToken, user));
    }

    @PostMapping("/register")
    public ResponseEntity<LoginResponse> register(@Valid @RequestBody RegisterRequest request) {
        var nickname = request.getNickname();
        var email = request.getEmail();
        var password = request.getPassword();

        User user = authService.registerUser(nickname, email, password, "register");
        var accessToken = tokenService.generateAccessToken(user);
        var refreshToken = tokenService.generateRefreshToken(user);
        ResponseCookie refreshTokenCookie = tokenService.generateRefreshTokenCookie(refreshToken);

        MDC.put("service", "register");
        MDC.put("nickname", nickname);
        logger.info("Użytkownik " + user.getNickname() + " zarejestrował się pomyślnie.");
        MDC.clear();

        return ResponseEntity.ok()
                .header("Set-Cookie", refreshTokenCookie.toString())
                .body(new LoginResponse("Zarejestrowano pomyślnie.", accessToken, user));
    }

}
