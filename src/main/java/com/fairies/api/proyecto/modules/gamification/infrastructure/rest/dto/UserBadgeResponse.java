package com.fairies.api.proyecto.modules.gamification.infrastructure.rest.dto;

import java.time.LocalDateTime;

public record UserBadgeResponse(
        Long badgeId,
        String name,
        String description,
        String url
) {
}