package com.fairies.api.proyecto.modules.book.application;

import com.fairies.api.proyecto.common.infrastructure.rest.exception.ResourceNotFoundException;
import com.fairies.api.proyecto.modules.book.domain.model.Book;
import com.fairies.api.proyecto.modules.book.infrastructure.persistence.BookRepository;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
public class UpdateBookUseCase {
    private final BookRepository repository;

    public UpdateBookUseCase(BookRepository repository) {
        this.repository = repository;
    }

    public Book execute(UUID id, Book updatedFields) {
        if (id == null) {
            throw new IllegalArgumentException("El ID no puede ser nulo.");
        }
        return null;
    }
}
