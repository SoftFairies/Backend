package com.fairies.api.proyecto.modules.readingsession.infrastructure.rest.dto;

import java.time.LocalDate;
import java.util.UUID;
import lombok.Data;

@Data
public class ReadingSessionResponse {
    private UUID id;
    private UUID libroId;
    private UUID usuarioId;
    private LocalDate fecha;
    private Integer segundosLeidos;
    private Integer paginasAvanzadas;
}