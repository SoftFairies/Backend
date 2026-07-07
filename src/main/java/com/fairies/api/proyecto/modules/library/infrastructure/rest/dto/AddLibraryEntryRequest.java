package com.fairies.api.proyecto.modules.library.infrastructure.rest.dto;

import com.fairies.api.proyecto.modules.book.infrastructure.rest.dto.CreateBookRequest;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record AddLibraryEntryRequest(
        UUID bookId,
        CreateBookRequest newBook,
        @NotNull Long readingStatusId,
        @NotNull Long formatId,
        Integer totalChapter,
        Integer totalPage,
        boolean isFavorite
) {}