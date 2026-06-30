package com.fairies.api.proyecto.modules.library.infrastructure.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.Set;
import java.util.UUID;

public record BookExternalSyncRequest(
        String isbn,

        @NotBlank(message = "El título es obligatorio para libros nuevos")
        String title,

        @NotBlank(message = "El origen es obligatorio")
        String origin,

        String coverType,
        String coverValue,
        Integer defaultChapters,
        Integer defaultPages,

        @NotNull(message = "El formato es obligatorio")
        UUID formatId,

        @NotEmpty(message = "El libro debe tener al menos un autor")
        Set<UUID> authorIds,

        @NotEmpty(message = "El libro debe tener al menos un género")
        Set<UUID> genreIds
) {}
