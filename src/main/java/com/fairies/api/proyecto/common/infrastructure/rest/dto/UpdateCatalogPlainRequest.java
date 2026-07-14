package com.fairies.api.proyecto.common.infrastructure.rest.dto;

public record UpdateCatalogPlainRequest(

        String name,

        String description
) {
    public UpdateCatalogPlainRequest {
        if ((name == null || name.isBlank()) && (description == null || description.isBlank())) {
            throw new IllegalArgumentException("Debe proporcionar al menos un campo para actualizar: nombre o descripción.");
        }
    }
}