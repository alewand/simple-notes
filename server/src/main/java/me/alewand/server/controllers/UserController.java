package me.alewand.server.controllers;

import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import me.alewand.server.models.User;
import me.alewand.server.services.UserService;
import me.alewand.server.types.requests.ChangeUserInfoFieldRequest;
import me.alewand.server.types.responses.CommonResponse;

import org.jboss.logging.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class.getName());

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/get")
    public ResponseEntity<User> getUser(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(user);
    }

    @PostMapping("/change-info")
    public ResponseEntity<CommonResponse> changeUserInfoField(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody ChangeUserInfoFieldRequest request) {

        var fieldName = request.getFieldName().trim();
        var fieldValue = request.getFieldValue().trim();

        userService.changeUserInfoField(fieldName, fieldValue, user, "change-user-info");

        MDC.put("service", "change-user-info");
        MDC.put("nickname", user.getNickname());
        MDC.put("fieldName", fieldName);
        MDC.put("fieldValue", fieldValue);
        logger.info(
                "Użytkownik " + user.getNickname() + " zmienił informacje o sobie - " + fieldName + ".");
        MDC.clear();

        return ResponseEntity.ok(new CommonResponse("Dane zostały zmienione pomyślnie."));
    }

}
