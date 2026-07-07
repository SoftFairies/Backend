package com.fairies.api.proyecto.modules.readingStatus.application;

import com.fairies.api.proyecto.modules.readingStatus.domain.model.ReadingStatus;
import com.fairies.api.proyecto.modules.readingStatus.infrastructure.persistence.ReadingStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class GetAllReadingStatusUseCase {
    private final ReadingStatusRepository repository;

    @Transactional(readOnly = true)
    public Page<ReadingStatus> execute(Pageable pageable) {
        return repository.findAll(pageable);
    }
}
