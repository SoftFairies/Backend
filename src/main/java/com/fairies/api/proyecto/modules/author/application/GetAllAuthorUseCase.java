package com.fairies.api.proyecto.modules.author.application;

import com.fairies.api.proyecto.modules.author.domain.model.Author;
import com.fairies.api.proyecto.modules.author.infrastructure.persistence.AuthorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class GetAllAuthorUseCase {
    private final AuthorRepository repository;

    public GetAllAuthorUseCase(AuthorRepository repository) {
        this.repository = repository;
    }

    public Page<Author> execute(String query, Pageable pageable) {
        if (query != null && !query.isBlank()) {
            return repository.findByNameContainingIgnoreCase(query, pageable);
        }
        return repository.findAll(pageable);
    }
}
