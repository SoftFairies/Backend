package com.fairies.api.proyecto.modules.book.application;

import com.fairies.api.proyecto.common.infrastructure.rest.exception.ResourceNotFoundException;
import com.fairies.api.proyecto.modules.book.domain.model.Book;
import com.fairies.api.proyecto.modules.book.infrastructure.persistence.BookRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Component
public class DeleteBookUseCase {

    private final BookRepository repository;

    public DeleteBookUseCase(BookRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void execute(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("El ID no puede ser nulo.");
        }
        Book entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book cannot be deleted. ID not found: " + id));
        repository.delete(entity);
    }
}