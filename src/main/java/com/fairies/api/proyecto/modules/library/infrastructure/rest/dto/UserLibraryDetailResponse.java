package com.fairies.api.proyecto.modules.library.infrastructure.rest.dto;

import java.util.UUID;

public record UserLibraryDetailResponse(
        UUID libraryId,
        UUID bookId,
        String title,
        Integer totalChapters,
        Integer totalPages,
        String coverType,
        String coverValue,
        String isbn,
        String origin,
        String readingStatusName,
        Integer currentChapter,
        Integer currentPage,
        boolean isFavorite
) {}