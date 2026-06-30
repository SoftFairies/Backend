package com.fairies.api.proyecto.common.infrastructure.rest.dto;

public record UpdateCatalogPlainRequest(
        String name,
        String description
) {}