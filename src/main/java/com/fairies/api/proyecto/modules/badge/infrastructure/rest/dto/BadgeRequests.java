package com.fairies.api.proyecto.modules.badge.infrastructure.rest.dto;

import jakarta.validation.constraints.NotBlank;

public record BadgeRequests(
        @NotBlank(message = "El nombre es mandatorio")
        String name,

        String description
) {}
