package com.fairies.api.proyecto.modules.author.application;

import com.fairies.api.proyecto.modules.author.domain.model.Author;
import com.fairies.api.proyecto.modules.author.infrastructure.persistence.AuthorRepository;
import org.springframework.stereotype.Component;

@Component
public class AddAuthorUseCase {
    private final AuthorRepository repository;

    public AddAuthorUseCase(AuthorRepository repository) {
        this.repository = repository;
    }

    public Author execute(Author entity) {
        if (entity == null) {
            throw new IllegalArgumentException("La entidad no puede ser nula.");
        }
        if (entity.getName() == null || entity.getName().isBlank()) {
            throw new IllegalArgumentException("El nombre es obligatorio.");
        }

        entity.setDeleted(false);
        return repository.save(entity);
    }
}
