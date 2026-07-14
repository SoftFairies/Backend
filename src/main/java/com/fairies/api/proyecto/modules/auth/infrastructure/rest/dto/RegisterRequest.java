package com.fairies.api.proyecto.modules.auth.infrastructure.rest.dto;

import jakarta.validation.constraints.*;

import java.util.UUID;

public record RegisterRequest(

        @NotBlank(message = "El nombre es obligatorio")
        String name,

        String lastname,

        @NotBlank(message = "El correo es obligatorio")
        @Email(message = "El formato del correo no es válido")
        String email,

        @NotBlank(message = "La contraseña es obligatoria")
        @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
        String password,

        UUID roleId
) {}