package com.fairies.api.proyecto.modules.library.infrastructure.rest.dto;

import jakarta.validation.constraints.NotBlank;

public record LibraryRequest(
        @NotBlank(message = "El nombre es obligatorio")
        String name,

        String description
) {}
