package com.fairies.api.proyecto.modules.book.infrastructure.rest.dto;

import jakarta.validation.constraints.NotBlank;
import java.util.Set;

public record CreateBookRequest(
        String isbn,
        @NotBlank String title,
        Set<EntityReferenceRequest> authors,
        Set<EntityReferenceRequest> genres,
        String cover
) {}