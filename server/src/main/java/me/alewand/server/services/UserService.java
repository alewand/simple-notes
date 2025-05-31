package me.alewand.server.services;

import java.util.Map;

import org.springframework.stereotype.Service;

import me.alewand.server.errors.UserInfoFieldToChangeDoesntExistsException;
import me.alewand.server.models.User;
import me.alewand.server.repositories.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AuthService authService;
    private final ValidationService validationService;

    public UserService(UserRepository userRepository, AuthService authService, ValidationService validationService) {
        this.authService = authService;
        this.validationService = validationService;
        this.userRepository = userRepository;
    }

    /**
     * Changes a specified field of the user information.
     *
     * @param fieldName  The name of the field to change (e.g., "nickname",
     *                   "email").
     * @param fieldValue The new value for the field.
     * @param user       The user whose information is being changed.
     * @param service    The service context in which the change is made (for
     *                   logging).
     */
    public void changeUserInfoField(String fieldName, String fieldValue, User user, String service) {
        switch (fieldName) {
            case "nickname" -> {
                authService.isNicknameTaken(fieldValue, service);
                user.setNickname(fieldValue);
                validationService.validate(user);
                userRepository.save(user);
            }
            case "email" -> {
                authService.isEmailTaken(fieldValue, service);
                user.setEmail(fieldValue);
                validationService.validate(user);
                userRepository.save(user);
            }
            default -> {
                throw new UserInfoFieldToChangeDoesntExistsException(Map.of("service", service, "nickname",
                        user.getNickname(), "fieldName", fieldName, "fieldValue", fieldValue));
            }
        }
    }

}
