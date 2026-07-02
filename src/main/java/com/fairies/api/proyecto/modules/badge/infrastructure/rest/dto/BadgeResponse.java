package com.fairies.api.proyecto.modules.badge.infrastructure.rest.dto;



public record BadgeResponse(
        Long id,
        String name,
        String description,
        String url
) {}
