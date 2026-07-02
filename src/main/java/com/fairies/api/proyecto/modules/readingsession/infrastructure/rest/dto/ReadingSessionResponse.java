package com.fairies.api.proyecto.modules.readingsession.infrastructure.rest.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.UUID;

public record ReadingSessionResponse(
        @NotNull UUID usuarioId,
        @NotNull Long libroId,
        @NotNull LocalDate fecha,
        @NotNull @Positive Long minutosLeidos,
        @NotNull Long paginasAvanzadas
) {}