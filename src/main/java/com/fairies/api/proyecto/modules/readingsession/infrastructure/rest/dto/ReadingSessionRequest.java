package com.fairies.api.proyecto.modules.readingsession.infrastructure.rest.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import java.time.LocalDate;
import java.util.UUID;

public record ReadingSessionRequest(

        LocalDate date,

        @NotNull(message = "Los segundos leídos son obligatorios.")
        @Positive(message = "Los segundos leídos deben ser mayores a 0.")
        Integer secondsRead,

        @Positive(message = "Las páginas leídas no pueden ser un número negativo.")
        Integer pagesRead,

        @Positive(message = "Los capítulos leídos no pueden ser un número negativo.")
        Integer chaptersRead
) {}