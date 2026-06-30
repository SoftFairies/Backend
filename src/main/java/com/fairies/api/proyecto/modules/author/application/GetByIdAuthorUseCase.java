package com.fairies.api.proyecto.modules.author.application;

import com.fairies.api.proyecto.common.infrastructure.rest.exception.ResourceNotFoundException;
import com.fairies.api.proyecto.modules.author.domain.model.Author;
import com.fairies.api.proyecto.modules.author.infrastructure.persistence.AuthorRepository;
import org.springframework.stereotype.Component;


@Component
public class GetByIdAuthorUseCase {
    private final AuthorRepository repository;

    public GetByIdAuthorUseCase(AuthorRepository repository) {
        this.repository = repository;
    }

    public Author execute(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("El ID no puede ser nulo.");
        }
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with ID: " + id));
    }
}
