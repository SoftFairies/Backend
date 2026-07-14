package com.fairies.api.proyecto.modules.library.infrastructure.rest.dto;


public record UpdateLibraryEntryRequest(
        String title,
        Long readingStatusId,
        Long formatId,
        Integer totalChapter,
        Integer totalPage,
        Boolean isFavorite
) {}