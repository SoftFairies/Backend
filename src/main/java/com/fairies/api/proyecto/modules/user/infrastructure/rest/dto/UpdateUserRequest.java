package com.fairies.api.proyecto.modules.user.infrastructure.rest.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateUserRequest(
        @NotBlank(message = "El nombre no puede estar vacío")
        String name,
        String lastname,
        String email,
        String password,
        Long pictureId
) {}