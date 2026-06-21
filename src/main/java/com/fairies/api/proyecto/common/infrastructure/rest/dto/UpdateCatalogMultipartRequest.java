package com.fairies.api.proyecto.common.infrastructure.rest.dto;

import org.springframework.web.multipart.MultipartFile;

public record UpdateCatalogMultipartRequest(
        MultipartFile file,
        String name,
        String description
) {
    public UpdateCatalogMultipartRequest {
        if ((file == null || file.isEmpty()) &&
                (name == null || name.isBlank()) &&
                (description == null || description.isBlank())) {
            throw new IllegalArgumentException("Debe proporcionar al menos un campo para actualizar (archivo, nombre o descripción).");
        }
    }
}