package me.alewand.server.types.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class NoteRequest {

    @NotBlank(message = "Tytuł notatki nie może być pusty.")
    @Size(min = 3, max = 100, message = "Tytuł notatki musi mieć od 3 do 100 znaków.")
    private String title;
    private String content;
}
