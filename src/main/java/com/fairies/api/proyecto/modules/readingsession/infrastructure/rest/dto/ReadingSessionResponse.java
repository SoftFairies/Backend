package com.fairies.api.proyecto.modules.readingsession.infrastructure.rest.dto;

import com.fairies.api.proyecto.modules.book.domain.model.Book;
import java.time.LocalDate;
import lombok.Data;

@Data
public class ReadingSessionResponse {
    private Long id;
    private String usuarioId;
    private Book book;
    private LocalDate fecha;
    private Integer segundosLeidos;
    private Integer paginasAvanzadas;
}