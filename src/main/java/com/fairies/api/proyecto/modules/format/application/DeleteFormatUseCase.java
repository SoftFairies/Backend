package com.fairies.api.proyecto.modules.format.application;

import com.fairies.api.proyecto.common.infrastructure.rest.exception.ResourceNotFoundException;
import com.fairies.api.proyecto.modules.format.infrastructure.persistence.FormatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@RequiredArgsConstructor
public class DeleteFormatUseCase {
    private final FormatRepository repository;

    @Transactional
    public void execute(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("El ID no puede ser nulo.");
        }
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Format cannot be deleted. ID not found: " + id);
        }
        repository.deleteById(id);
    }
}
