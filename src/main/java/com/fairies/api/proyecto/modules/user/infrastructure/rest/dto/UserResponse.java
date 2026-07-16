package com.fairies.api.proyecto.modules.user.infrastructure.rest.dto;

import java.util.UUID;

public record UserResponse(
        UUID id,
        String name,
        String lastname,
        String email,
        String roleName,
        Long pictureId,
        String pictureUrl,
        Integer annualGoal,
        boolean active
) {}