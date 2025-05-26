package me.alewand.server.types.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegisterRequest {

    @NotNull(message = "Nick jest wymagany.")
    private String nickname;

    @NotNull(message = "Email jest wymagany.")
    private String email;

    @NotNull(message = "Hasło jest wymagane.")
    @NotBlank(message = "Hasło nie może być puste.")
    private String password;
}
