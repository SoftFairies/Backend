package com.fairies.api.proyecto.modules.library.infrastructure.rest.dto;


public record UpdateLibraryEntryRequest(
        String title,
        Long readingStatusId,
        Long formatId,
        Integer currentChapter,
        Integer currentPage,
        Boolean isFavorite
) {}