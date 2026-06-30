package com.fairies.api.proyecto.modules.readingStatus.application;

import com.fairies.api.proyecto.modules.readingStatus.domain.model.ReadingStatus;
import com.fairies.api.proyecto.modules.readingStatus.infrastructure.persistence.ReadingStatusRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class GetAllReadingStatusUseCase {
    private final ReadingStatusRepository repository;

    public GetAllReadingStatusUseCase(ReadingStatusRepository repository) {
        this.repository = repository;
    }

    public Page<ReadingStatus> execute(Pageable pageable) {
        return repository.findAll(pageable);
    }
}
