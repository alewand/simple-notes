package me.alewand.server.types.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginRequest {

    @NotNull(message = "Login jest wymagany.")
    @NotBlank(message = "Login nie może być pusty.")
    private String nicknameOrEmail;

    @NotNull(message = "Hasło jest wymagane.")
    @NotBlank(message = "Hasło nie może być puste.")
    private String password;
}
