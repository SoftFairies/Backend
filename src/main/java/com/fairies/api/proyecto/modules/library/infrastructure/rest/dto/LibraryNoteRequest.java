package com.fairies.api.proyecto.modules.library.infrastructure.rest.dto;

import jakarta.validation.constraints.NotBlank;

public record LibraryNoteRequest(
        @NotBlank String content,
        Integer chapter,
        Integer page
) {}
