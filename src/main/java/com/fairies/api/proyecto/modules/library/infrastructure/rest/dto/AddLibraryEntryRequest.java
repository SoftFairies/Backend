package com.fairies.api.proyecto.modules.library.infrastructure.rest.dto;

import com.fairies.api.proyecto.modules.book.infrastructure.rest.dto.CreateBookRequest;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record AddLibraryEntryRequest(

        @NotNull(message = "El ID del libro es obligatorio")
        UUID bookId,

        CreateBookRequest newBook,

        @NotNull(message = "El estado de lectura es obligatorio")
        Long readingStatusId,

        @NotNull(message = "El formato es obligatorio")
        Long formatId,

        @Min(value = 1, message = "El total de capítulos debe ser al menos 1")
        @Max(value = 10000, message = "El total de capítulos no puede exceder los 10,000")
        Integer totalChapter,

        @Min(value = 1, message = "El total de páginas debe ser al menos 1")
        @Max(value = 100000, message = "El total de páginas no puede exceder las 100,000")
        Integer totalPage,

        boolean isFavorite
) {}