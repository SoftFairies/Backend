package com.fairies.api.proyecto.modules.readingStatus.application;

import com.fairies.api.proyecto.common.infrastructure.rest.exception.ResourceNotFoundException;
import com.fairies.api.proyecto.modules.readingStatus.domain.model.ReadingStatus;
import com.fairies.api.proyecto.modules.readingStatus.infrastructure.persistence.ReadingStatusRepository;
import org.springframework.stereotype.Component;


@Component
public class UpdateReadingStatusUseCase {
    private final ReadingStatusRepository repository;

    public UpdateReadingStatusUseCase(ReadingStatusRepository repository) {
        this.repository = repository;
    }

    public ReadingStatus execute(Long id, ReadingStatus updatedFields) {
        if (id == null) {
            throw new IllegalArgumentException("El ID no puede ser nulo.");
        }
        if (updatedFields == null || updatedFields.getName() == null || updatedFields.getName().isBlank()) {
            throw new IllegalArgumentException("El nombre es obligatorio para actualizar.");
        }

        ReadingStatus existingEntity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ReadingStatus not found with ID: " + id));

        existingEntity.setName(updatedFields.getName());
        existingEntity.setDescription(updatedFields.getDescription());

        return repository.save(existingEntity);
    }
}
