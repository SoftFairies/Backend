package com.fairies.api.proyecto.modules.library.infrastructure.rest.dto;

import java.util.Set;

public record BookExternalSyncRequest(
        String isbn,
        String title,
        String originalTitle,
        Integer defaultChapters,
        Integer defaultPages,
        String origin,
        String coverType,
        String coverValue,
        Long formatId,
        Set<Long> authorIds,
        Set<Long> genreIds
) {}