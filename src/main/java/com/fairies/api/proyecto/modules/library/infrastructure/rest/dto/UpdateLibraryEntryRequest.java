package com.fairies.api.proyecto.modules.library.infrastructure.rest.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record UpdateLibraryEntryRequest(

        String title,

        Long readingStatusId,

        Long formatId,

        @Min(value = 1, message = "El total de capítulos debe ser al menos 1")
        @Max(value = 10000, message = "El total de capítulos no puede exceder los 10,000")
        Integer totalChapter,

        @Min(value = 1, message = "El total de páginas debe ser al menos 1")
        @Max(value = 100000, message = "El total de páginas no puede exceder las 100,000")
        Integer totalPage,

        Boolean isFavorite
) {
    public UpdateLibraryEntryRequest {
        if (title == null && readingStatusId == null && formatId == null && totalChapter == null && totalPage == null && isFavorite == null) {
            throw new IllegalArgumentException("Debe proporcionar al menos un campo para actualizar: título, estado, formato, capítulos, páginas o favorito.");
        }
    }
}