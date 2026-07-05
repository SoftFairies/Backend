package com.fairies.api.proyecto.modules.readingsession.infrastructure.rest.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;
import lombok.Data;

@Data
public class ReadingSessionRequest {
    @NotNull(message = "El ID de usuario es obligatorio.")
    private String usuarioId;

    @NotNull(message = "El ID del libro es obligatorio.")
    private UUID libroId;

    @NotNull(message = "La fecha es obligatoria.")
    private LocalDate fecha;

    @NotNull(message = "Los segundos leídos son obligatorios.")
    private Integer segundosLeidos;

    @NotNull(message = "Las páginas avanzadas son obligatorios.")
    private Integer paginasAvanzadas;
}