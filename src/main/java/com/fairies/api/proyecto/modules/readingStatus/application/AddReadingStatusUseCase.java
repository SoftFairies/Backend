package com.fairies.api.proyecto.modules.readingStatus.application;

import com.fairies.api.proyecto.modules.readingStatus.domain.model.ReadingStatus;
import com.fairies.api.proyecto.modules.readingStatus.infrastructure.persistence.ReadingStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class AddReadingStatusUseCase {
    private final ReadingStatusRepository repository;

    @Transactional
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
