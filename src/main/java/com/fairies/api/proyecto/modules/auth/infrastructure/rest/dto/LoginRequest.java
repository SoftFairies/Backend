package com.fairies.api.proyecto.modules.auth.infrastructure.rest.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "El correo no puede estar vacío")
        @Email(message = "El formato del correo no es válido")
        String email,

        @NotBlank(message = "La contraseña no puede estar vacía")
        String password
) {}