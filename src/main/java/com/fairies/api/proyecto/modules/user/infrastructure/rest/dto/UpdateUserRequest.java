package com.fairies.api.proyecto.modules.user.infrastructure.rest.dto;

public record UpdateUserRequest(
        String name,
        String lastname,
        String email,
        String password,
        Long pictureId
) {}