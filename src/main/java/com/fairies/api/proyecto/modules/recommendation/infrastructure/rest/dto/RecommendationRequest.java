package com.fairies.api.proyecto.modules.recommendation.infrastructure.rest.dto;

import java.util.Set;

public record RecommendationRequest(
        Set<Long> formatIds,
        Set<Long> genreIds
) {}