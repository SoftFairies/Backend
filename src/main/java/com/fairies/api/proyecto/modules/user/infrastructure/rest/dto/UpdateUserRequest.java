package com.fairies.api.proyecto.modules.user.infrastructure.rest.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public record UpdateUserRequest(

        @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
        String name,

        @Size(min = 2, max = 50, message = "El apellido debe tener entre 2 y 50 caracteres")
        String lastname,

        @Email(message = "El formato del correo no es válido")
        String email,

        @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
        String password,

        @Min(value = 1, message = "El ID de la imagen no es válido")
        Long pictureId,

        @Min(value = 1, message = "La meta anual no es válida")
        Integer annualGoal
) {
        public UpdateUserRequest {
                if (name == null && lastname == null && email == null &&
                        password == null && pictureId == null && annualGoal == null) {
                        throw new IllegalArgumentException("Debe proporcionar al menos un campo para actualizar: nombre, apellido, correo, contraseña, imagen o meta anual.");
                }
        }
}