package me.alewand.server.services;

import java.util.Map;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import me.alewand.server.errors.EmailTakenException;
import me.alewand.server.errors.InvalidPasswordDuringAuthenticationException;
import me.alewand.server.errors.NicknameTakenException;
import me.alewand.server.errors.UserNotFoundDuringAuthenticationException;
import me.alewand.server.models.User;
import me.alewand.server.repositories.UserRepository;
import me.alewand.server.types.others.PasswordWrapper;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ValidationService validationService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder,
            ValidationService validationService) {
        this.validationService = validationService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Authenticates a user by nickname or email and password.
     *
     * @param nicknameOrEmail the user's nickname or email
     * @param password        the user's password
     * @param service         the service name for context (e.g., "login")
     * @return the authenticated User object
     * @throws UserNotFoundDuringAuthenticationException    if the user is not found
     * @throws InvalidPasswordDuringAuthenticationException if the password is
     *                                                      incorrect
     */
    public User getAuthenticatedUser(String nicknameOrEmail, String password, String service) {
        User user = userRepository.findByNicknameOrEmail(nicknameOrEmail, nicknameOrEmail)
                .orElseThrow(() -> new UserNotFoundDuringAuthenticationException(
                        Map.of("service", service, "nicknameOrEmail", nicknameOrEmail)));

        var isPasswordCorrect = passwordEncoder.matches(password, user.getPassword());

        if (!isPasswordCorrect)
            throw new InvalidPasswordDuringAuthenticationException(
                    Map.of("nicknameOrEmail", nicknameOrEmail));

        return user;
    }

    /**
     * Registers a new user with the provided nickname, email, and password.
     * 
     * @param nickname the user's nickname
     * @param email    the user's email
     * @param password the user's password
     * @param service  the service name for context (e.g., "registration")
     * @return the registered User object
     * @throws NicknameTakenException if the nickname is already taken
     * @throws EmailTakenException    if the email is already taken
     */
    public User registerUser(String nickname, String email, String password, String service) {
        isNicknameTaken(nickname, service);
        isEmailTaken(email, service);

        var passwordValidation = new PasswordWrapper(password);
        validationService.validate(passwordValidation);

        var hashedPassword = passwordEncoder.encode(passwordValidation.getPassword());

        User user = new User(nickname, email, hashedPassword);
        validationService.validate(user);

        return userRepository.save(user);
    }

    /**
     * Changes the password of an existing user.
     *
     * @param user        the User object whose password is to be changed
     * @param newPassword the new password for the user
     * @throws NicknameTakenException if the new nickname is already taken
     * @throws EmailTakenException    if the new email is already taken
     */
    public void changeUserPassword(User user, String newPassword) {
        var passwordValidation = new PasswordWrapper(newPassword);
        validationService.validate(passwordValidation);

        user.setPassword(passwordEncoder.encode(passwordValidation.getPassword()));
        userRepository.save(user);
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
            throw new NicknameTakenException(Map.of("service", service, "nickname", nickname));
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
            throw new EmailTakenException(Map.of("service", service, "email", email));
    }

    /**
     * Deletes a user from the repository.
     *
     * @param user the User object to delete
     */
    public void deleteUser(User user) {
        userRepository.delete(user);
    }

}
