package com.fairies.api.proyecto.modules.author.application;

import com.fairies.api.proyecto.modules.author.domain.model.Author;
import com.fairies.api.proyecto.modules.author.infrastructure.persistence.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class AddAuthorUseCase {
    private final AuthorRepository repository;

    @Transactional
    public Author execute(Author entity) {
        entity.setDeleted(false);
        return repository.save(entity);
    }
}
