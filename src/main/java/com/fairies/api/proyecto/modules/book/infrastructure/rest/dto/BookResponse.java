package com.fairies.api.proyecto.modules.book.infrastructure.rest.dto;

import java.util.UUID;

public record BookResponse(
        UUID id,
        String name,
        String description
) {}
