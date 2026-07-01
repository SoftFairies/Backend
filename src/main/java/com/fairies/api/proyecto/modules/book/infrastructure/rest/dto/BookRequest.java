package com.fairies.api.proyecto.modules.book.infrastructure.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Set;
import java.util.UUID;

public record BookRequest(
        String isbn,

        @NotBlank(message = "El título es obligatorio")
        String title,

        Integer defaultChapters,
        Integer defaultPages,

        @NotBlank(message = "El origen es obligatorio")
        String origin,

        @NotBlank(message = "El tipo de portada es obligatorio")
        String coverType,

        @NotBlank(message = "El valor de la portada es obligatorio")
        String coverValue,

        @NotNull(message = "El formato es obligatorio")
        Long formatId,

        Set<Long> authorIds,
        Set<Long> genreIds
) {}