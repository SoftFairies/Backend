package com.fairies.api.proyecto.modules.book.infrastructure.rest.dto;

public record EntityReferenceRequest(
        Long id,
        String name
) {
    public EntityReferenceRequest {
        if (id == null && (name == null || name.isBlank())) {
            throw new IllegalArgumentException("Debe proporcionar al menos un ID o un nombre válido para la referencia.");
        }
    }
}