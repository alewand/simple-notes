package me.alewand.server.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import me.alewand.server.models.User;
import me.alewand.server.services.AuthService;
import me.alewand.server.services.TokenService;
import me.alewand.server.types.requests.ConfirmRequest;
import me.alewand.server.types.requests.LoginRequest;
import me.alewand.server.types.requests.RegisterRequest;
import me.alewand.server.types.responses.CommonResponse;
import me.alewand.server.types.responses.LoginResponse;
import me.alewand.server.types.responses.RefreshResponse;

import org.jboss.logging.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
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

    @PostMapping("/register")
    public ResponseEntity<LoginResponse> register(@Valid @RequestBody RegisterRequest request) {
        var nickname = request.getNickname().trim();
        var email = request.getEmail().trim();
        var password = request.getPassword().trim();

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

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        var nicknameOrEmail = request.getNicknameOrEmail().trim();
        var password = request.getPassword().trim();

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

    @DeleteMapping("/logout")
    public ResponseEntity<CommonResponse> logout(@AuthenticationPrincipal User user,
            @CookieValue(name = "SimpleNotesSession") String refreshToken) {
        tokenService.revokeRefreshToken(refreshToken, "logout", user.getNickname());

        MDC.put("service", "logout");
        MDC.put("nickname", user.getNickname());
        logger.info("Użytkownik " + user.getNickname() + " wylogował się pomyślnie.");
        MDC.clear();

        var clearCookie = tokenService.clearRefreshTokenCookie();

        return ResponseEntity.ok()
                .header("Set-Cookie", clearCookie.toString())
                .body(new CommonResponse("Wylogowano pomyślnie."));
    }

    @DeleteMapping("/logout-from-all-devices")
    public ResponseEntity<CommonResponse> logoutFromAllDevices(@AuthenticationPrincipal User user) {
        tokenService.revokeAllRefreshTokens(user);

        MDC.put("service", "logout-from-all-devices");
        MDC.put("nickname", user.getNickname());
        logger.info("Użytkownik " + user.getNickname() + " wylogował się ze wszystkich urządzeń.");
        MDC.clear();

        var clearCookie = tokenService.clearRefreshTokenCookie();

        return ResponseEntity.ok()
                .header("Set-Cookie", clearCookie.toString())
                .body(new CommonResponse("Wylogowano ze wszystkich urządzeń."));
    }

    @PostMapping("/refresh")
    public ResponseEntity<RefreshResponse> refresh(
            @CookieValue(name = "SimpleNotesSession") String refreshToken) {
        var accessToken = tokenService.refreshAccessToken(refreshToken);
        return ResponseEntity.ok(new RefreshResponse("Odświeżono token dostępu.", accessToken));
    }

    @PostMapping("/delete-account")
    public ResponseEntity<CommonResponse> deleteAccount(@AuthenticationPrincipal User user,
            @Valid @RequestBody ConfirmRequest request) {
        var nickname = request.getNickname().trim();
        var password = request.getPassword().trim();

        var userFromConfirmation = authService.getAuthenticatedUser(nickname, password, "delete-account");

        if (!userFromConfirmation.getUserId().equals(user.getUserId())) {

            MDC.put("service", "delete-account");
            MDC.put("nicknameFromToken", user.getNickname());
            MDC.put("nicknameFromRequest", nickname);
            logger.info("Użytkownik " + user.getNickname()
                    + " próbował potwierdzić usunięcie swojego konta, ale użył danych innego użytkownika.");
            MDC.clear();

            return ResponseEntity.status(401)
                    .body(new CommonResponse("Nie prawidłowy nick lub hasło."));
        }

        authService.deleteUser(user);

        var clearCookie = tokenService.clearRefreshTokenCookie();

        MDC.put("service", "delete-account");
        MDC.put("nickname", nickname);
        logger.info("Użytkownik " + user.getNickname() + " usunął swoje konto.");
        MDC.clear();

        return ResponseEntity.ok()
                .header("Set-Cookie", clearCookie.toString())
                .body(new CommonResponse("Konto usunięte pomyślnie."));
    }

}
