package com.fairies.api.proyecto.modules.recommendation.infrastructure.rest.dto;

import java.util.Collections;
import java.util.Set;

public record RecommendationRequest(
        Set<Long> formatIds,
        Set<Long> genreIds
) {
    public RecommendationRequest {
        formatIds = (formatIds == null) ? Collections.emptySet() : formatIds;
        genreIds = (genreIds == null) ? Collections.emptySet() : genreIds;

        if (formatIds.isEmpty() && genreIds.isEmpty()) {
            throw new IllegalArgumentException("Debe proporcionar al menos un ID en formatos o géneros.");
        }
    }
}