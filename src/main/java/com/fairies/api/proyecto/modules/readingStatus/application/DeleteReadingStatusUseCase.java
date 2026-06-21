package com.fairies.api.proyecto.modules.readingStatus.application;

import com.fairies.api.proyecto.common.infrastructure.rest.exception.ResourceNotFoundException;
import com.fairies.api.proyecto.modules.readingStatus.domain.model.ReadingStatus;
import com.fairies.api.proyecto.modules.readingStatus.infrastructure.persistence.ReadingStatusRepository;
import org.springframework.stereotype.Component;


@Component
public class DeleteReadingStatusUseCase {
    private final ReadingStatusRepository repository;

    public DeleteReadingStatusUseCase(ReadingStatusRepository repository) {
        this.repository = repository;
    }

    public void execute(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("El ID no puede ser nulo.");
        }
        ReadingStatus entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ReadingStatus cannot be deleted. ID not found: " + id));

        repository.delete(entity);
    }
}
