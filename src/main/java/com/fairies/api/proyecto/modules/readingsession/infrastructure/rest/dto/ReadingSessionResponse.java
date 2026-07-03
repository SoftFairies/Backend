package com.fairies.api.proyecto.modules.readingsession.infrastructure.rest.dto;


import com.fairies.api.proyecto.modules.book.domain.model.Book; // 🔄 Ruta exacta de su entidad Book
import java.time.LocalDate;
import lombok.Data;

@Data
public class ReadingSessionResponse {
    private Long id;
    private String usuarioId;
    private Book book; // 🔄 Cambiado a Book
    private LocalDate fecha;
    private Integer minutesLeidos;
    private Integer paginasAvanzadas;
}