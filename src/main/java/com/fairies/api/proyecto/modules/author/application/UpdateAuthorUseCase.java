package com.fairies.api.proyecto.modules.author.application;

import com.fairies.api.proyecto.common.infrastructure.rest.exception.ResourceNotFoundException;
import com.fairies.api.proyecto.modules.author.domain.model.Author;
import com.fairies.api.proyecto.modules.author.infrastructure.persistence.AuthorRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class UpdateAuthorUseCase {
    private final AuthorRepository repository;

    @Transactional
    public Author execute(Long id, Author updatedFields) {
        if (id == null) {
            throw new IllegalArgumentException("El ID no puede ser nulo.");
        }
        if (updatedFields == null || updatedFields.getName() == null || updatedFields.getName().isBlank()) {
            throw new IllegalArgumentException("El nombre es obligatorio para actualizar.");
        }

        Author existingEntity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with ID: " + id));

        existingEntity.setName(updatedFields.getName());
        existingEntity.setDescription(updatedFields.getDescription());

        return repository.save(existingEntity);
    }
}
