package com.fairies.api.proyecto.modules.picture.infrastructure.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public record PictureRequests(
        @NotNull(message = "El archivo de imagen es obligatorio")
        MultipartFile file,
        @NotBlank(message = "El name es obligatorio")
        String name
) {}
