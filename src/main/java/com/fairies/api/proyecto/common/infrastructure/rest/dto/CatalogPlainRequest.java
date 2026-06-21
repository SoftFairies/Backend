package com.fairies.api.proyecto.common.infrastructure.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public record CatalogPlainRequest(
        @NotBlank(message = "El name es obligatorio")
        String name,
        String description
) {}