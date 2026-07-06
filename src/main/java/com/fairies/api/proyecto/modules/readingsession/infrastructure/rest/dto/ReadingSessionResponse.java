package com.fairies.api.proyecto.modules.readingsession.infrastructure.rest.dto;

import com.fairies.api.proyecto.modules.book.domain.model.Book;
import java.time.LocalDate;
import java.util.UUID;

import com.fairies.api.proyecto.modules.user.domain.model.User;
import lombok.Data;

@Data
public class ReadingSessionResponse {
    private UUID id;
    private User user;
    private Book book;
    private LocalDate fecha;
    private Integer segundosLeidos;
    private Integer paginasAvanzadas;
}