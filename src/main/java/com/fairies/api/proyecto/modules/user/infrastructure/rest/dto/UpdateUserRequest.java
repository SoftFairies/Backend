package com.fairies.api.proyecto.modules.user.infrastructure.rest.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public record UpdateUserRequest(

        @Size(min = 2, max = 50, message = "Si envías un nombre, debe tener entre 2 y 50 caracteres")
        String name,

        @Size(min = 2, max = 50, message = "Si envías un apellido, debe tener entre 2 y 50 caracteres")
        String lastname,

        @Email(message = "El formato del correo no es válido")
        String email,

        @Size(min = 8, message = "Si cambias la contraseña, debe tener al menos 8 caracteres")
        String password,

        @Min(value = 1, message = "Imagen no encontrada")
        Long pictureId,

        @Min(value = 1, message = "Meta invalida")
        Integer annualGoal

) {}