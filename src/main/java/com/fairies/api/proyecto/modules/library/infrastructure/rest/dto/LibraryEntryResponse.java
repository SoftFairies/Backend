package com.fairies.api.proyecto.modules.library.infrastructure.rest.dto;

import com.fairies.api.proyecto.modules.book.infrastructure.rest.dto.BookResponse;
import java.util.UUID;

public record LibraryEntryResponse(
        UUID id,
        BookResponse book,
        String readingStatusName,
        String formatName,
        Integer currentChapter,
        Integer currentPage,
        Integer totalPage,
        Integer totalChapter,
        boolean isFavorite
) {}