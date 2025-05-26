package me.alewand.server.types.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class RegisterRequest {

    @NotNull(message = "Nick jest wymagany.")
    private String nickname;

    @NotNull(message = "Email jest wymagany.")
    private String email;

    @NotNull(message = "Hasło jest wymagane.")
    @NotBlank(message = "Hasło nie może być puste.")
    private String password;

    public RegisterRequest(String nickname, String email, String password) {
        this.nickname = nickname.trim();
        this.email = email.trim();
        this.password = password.trim();
    }
}
