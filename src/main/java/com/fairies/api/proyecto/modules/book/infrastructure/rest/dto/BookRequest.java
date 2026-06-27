package com.fairies.api.proyecto.modules.book.infrastructure.rest.dto;

import jakarta.validation.constraints.NotBlank;

public record BookRequest(
        @NotBlank(message = "El nombre es obligatorio")
        String name,

        String description
) {}
