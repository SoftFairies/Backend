package com.fairies.api.proyecto.modules.library.infrastructure.rest.dto;

import java.util.UUID;

public record LibraryResponse(
        UUID id,
        String name,
        String description
) {}
