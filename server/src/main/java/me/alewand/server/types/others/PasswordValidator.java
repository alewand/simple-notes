package me.alewand.server.types.others;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PasswordValidator {

    @NotBlank(message = "Hasło nie może być puste.")
    @Pattern(regexp = "^\\S*$", message = "Hasło nie może zawierać spacji.")
    @Size(min = 8, max = 64, message = "Hasło musi mieć od 8 do 64 znaków.")
    @Pattern(regexp = ".*[A-Z].*", message = "Hasło musi zawierać co najmniej jedną wielką literę.")
    @Pattern(regexp = ".*\\d.*", message = "Hasło musi zawierać co najmniej jedną cyfrę.")
    @Pattern(regexp = ".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?].*", message = "Hasło musi zawierać co najmniej jeden znak specjalny.")
    private String password;
}
