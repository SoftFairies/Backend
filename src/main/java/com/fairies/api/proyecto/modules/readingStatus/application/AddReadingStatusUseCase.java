package com.fairies.api.proyecto.modules.readingStatus.application;

import com.fairies.api.proyecto.modules.readingStatus.domain.model.ReadingStatus;
import com.fairies.api.proyecto.modules.readingStatus.infrastructure.persistence.ReadingStatusRepository;
import org.springframework.stereotype.Component;

@Component
public class AddReadingStatusUseCase {
    private final ReadingStatusRepository repository;

    public AddReadingStatusUseCase(ReadingStatusRepository repository) {
        this.repository = repository;
    }

    public ReadingStatus execute(ReadingStatus entity) {
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
