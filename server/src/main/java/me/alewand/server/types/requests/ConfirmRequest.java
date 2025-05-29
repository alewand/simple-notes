package me.alewand.server.types.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ConfirmRequest {

    @NotNull(message = "Nick jest wymagany.")
    @NotBlank(message = "Nick nie może być pusty.")
    private String nickname;

    @NotNull(message = "Hasło jest wymagane.")
    @NotBlank(message = "Hasło nie może być puste.")
    private String password;
}
