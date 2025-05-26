package me.alewand.server.services;

import java.util.Map;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import me.alewand.server.constants.Constants;
import me.alewand.server.errors.EmailTakenException;
import me.alewand.server.errors.InvalidPasswordDuringAuthenticationException;
import me.alewand.server.errors.NicknameTakenException;
import me.alewand.server.errors.UserNotFoundDuringAuthenticationException;
import me.alewand.server.models.User;
import me.alewand.server.repositories.UserRepository;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Authenticates a user by nickname or email and password.
     *
     * @param nicknameOrEmail the user's nickname or email
     * @param password        the user's password
     * @return the authenticated User object
     * @throws UserNotFoundDuringAuthenticationException    if the user is not found
     * @throws InvalidPasswordDuringAuthenticationException if the password is
     *                                                      incorrect
     */
    public User getAuthenticatedUser(String nicknameOrEmail, String password, String service) {
        User user = userRepository.findByNicknameOrEmail(nicknameOrEmail, nicknameOrEmail)
                .orElseThrow(() -> new UserNotFoundDuringAuthenticationException(
                        Map.of(Constants.SERVICE_STR, service, "nicknameOrEmail", nicknameOrEmail)));

        var isPasswordCorrect = passwordEncoder.matches(password, user.getPassword());

        if (!isPasswordCorrect)
            throw new InvalidPasswordDuringAuthenticationException(
                    Map.of("nicknameOrEmail", nicknameOrEmail));

        return user;
    }

    /**
     * Checks if a nickname is already taken.
     *
     * @param nickname the nickname to check
     * @param service  the service name for context (e.g., "registration")
     * @throws NicknameTakenException if the nickname is already taken
     */
    public void isNicknameTaken(String nickname, String service) {
        if (userRepository.existsByNickname(nickname))
            throw new NicknameTakenException(Map.of(Constants.SERVICE_STR, service, "nickname", nickname));
    }

    /**
     * Checks if an email is already taken.
     *
     * @param email   the email to check
     * @param service the service name for context (e.g., "registration")
     * @throws EmailTakenException if the email is already taken
     */
    public void isEmailTaken(String email, String service) {
        if (userRepository.existsByEmail(email))
            throw new EmailTakenException(Map.of(Constants.SERVICE_STR, service, "email", email));
    }

}
