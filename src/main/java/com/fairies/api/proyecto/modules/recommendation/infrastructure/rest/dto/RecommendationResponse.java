package com.fairies.api.proyecto.modules.recommendation.infrastructure.rest.dto;

import java.util.Set;
import java.util.UUID;

public record RecommendationResponse(
        UUID id,
        UUID userId,
        Set<PreferenceItem> formats,
        Set<PreferenceItem> genres
) {}