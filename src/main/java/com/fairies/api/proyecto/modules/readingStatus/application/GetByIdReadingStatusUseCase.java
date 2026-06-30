package com.fairies.api.proyecto.modules.readingStatus.application;

import com.fairies.api.proyecto.common.infrastructure.rest.exception.ResourceNotFoundException;
import com.fairies.api.proyecto.modules.readingStatus.domain.model.ReadingStatus;
import com.fairies.api.proyecto.modules.readingStatus.infrastructure.persistence.ReadingStatusRepository;
import org.springframework.stereotype.Component;


@Component
public class GetByIdReadingStatusUseCase {
    private final ReadingStatusRepository repository;

    public GetByIdReadingStatusUseCase(ReadingStatusRepository repository) {
        this.repository = repository;
    }

    public ReadingStatus execute(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("El ID no puede ser nulo.");
        }
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ReadingStatus not found with ID: " + id));
    }
}
