package com.fairies.api.proyecto.modules.library.infrastructure.rest.dto;

import java.time.LocalDateTime;

public record LibraryNoteResponse(
        Long id,
        String content,
        Integer chapter,
        Integer page,
        String bookTitle,
        LocalDateTime createdAt
) {}