package com.fairies.api.proyecto.modules.author.application;

import com.fairies.api.proyecto.common.infrastructure.rest.exception.ResourceNotFoundException;
import com.fairies.api.proyecto.modules.author.domain.model.Author;
import com.fairies.api.proyecto.modules.author.infrastructure.persistence.AuthorRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class DeleteAuthorUseCase {
    private final AuthorRepository repository;

    @Transactional
    public void execute(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("El ID no puede ser nulo.");
        }
        Author entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author cannot be deleted. ID not found: " + id));

        repository.delete(entity);
    }
}
