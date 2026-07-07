package com.fairies.api.proyecto.modules.readingsession.infrastructure.rest.dto;

import java.time.LocalDate;
import java.util.UUID;

public record ReadingSessionResponse(
        UUID id,
        UUID libraryId,
        String bookTitle,
        String bookCover,
        LocalDate date,
        Integer secondsRead,
        Integer pagesRead,
        Integer chaptersRead
) {}