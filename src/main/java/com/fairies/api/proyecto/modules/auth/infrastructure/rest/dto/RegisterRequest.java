package com.fairies.api.proyecto.modules.auth.infrastructure.rest.dto;

import jakarta.validation.constraints.*;

import java.util.UUID;

public record RegisterRequest(
        @NotBlank(message = "El nombre no puede estar vacío")
        String name,

        String lastname,

        @NotBlank(message = "El correo no puede estar vacío")
        @Email(message = "El formato del correo no es válido")
        String email,

        @NotBlank(message = "La contraseña no puede estar vacía")
        @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
        String password,

        UUID roleId
) {}