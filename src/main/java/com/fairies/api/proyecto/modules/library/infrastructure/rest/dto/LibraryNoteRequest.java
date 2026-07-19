package com.fairies.api.proyecto.modules.library.infrastructure.rest.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record LibraryNoteRequest(
        @NotBlank(message = "El contenido es obligatorio")
        String content,

        @Min(value = 1, message = "El capítulo debe ser al menos 1")
        @Max(value = 10000, message = "El capítulo no puede exceder los 10,000")
        Integer chapter,

        @Min(value = 1, message = "La página debe ser al menos 1")
        @Max(value = 100000, message = "La página no puede exceder las 100,000")
        Integer page
) {
}