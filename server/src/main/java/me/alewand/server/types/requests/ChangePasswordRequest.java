package me.alewand.server.types.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChangePasswordRequest {

    @NotNull(message = "Stare hasło nie może być puste.")
    @NotBlank(message = "Stare hasło nie może być puste.")
    private final String oldPassword;

    @NotNull(message = "Nowe hasło nie może być puste.")
    @NotBlank(message = "Nowe hasło nie może być puste.")
    private final String newPassword;
}
