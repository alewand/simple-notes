package me.alewand.server.types.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChangeUserInfoFieldRequest {

    @NotNull(message = "Pole do zmiany nie może być puste.")
    @NotBlank(message = "Pole do zmiany nie może być puste.")
    private String fieldName;

    @NotNull(message = "Wartość pola nie może być pusta.")
    @NotBlank(message = "Wartość pola nie może być pusta.")
    private String fieldValue;

}
