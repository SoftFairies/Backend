// refactor(mailbox): use java record for SendLetterRequest DTO
package com.fairies.api.proyecto.modules.mailbox.infrastructure.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record SendLetterRequest(
        @NotNull(message = "El ID del libro es obligatorio")
        UUID bookId,

        @NotBlank(message = "El contenido de la recomendación no puede estar vacío")
        String content
) {}