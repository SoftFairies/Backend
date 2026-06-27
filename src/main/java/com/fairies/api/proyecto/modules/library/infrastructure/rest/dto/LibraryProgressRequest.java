package com.fairies.api.proyecto.modules.library.infrastructure.rest.dto;

public record LibraryProgressRequest(
        Long readingStatusId,
        Integer currentChapter,
        Integer currentPage,
        Boolean isFavorite
) {}