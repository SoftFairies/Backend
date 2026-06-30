package com.fairies.api.proyecto.modules.auth.infrastructure.rest.dto;

import java.util.UUID;

public record AuthResponse(
        UUID id,
        String email,
        String role,
        String token
) {}