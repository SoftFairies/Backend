package com.fairies.api.proyecto.modules.gender.application;

import com.fairies.api.proyecto.common.infrastructure.rest.exception.ResourceNotFoundException;
import com.fairies.api.proyecto.modules.gender.domain.model.Gender;
import com.fairies.api.proyecto.modules.gender.infrastructure.persistence.GenderRepository;
import org.springframework.stereotype.Component;

@Component
public class GetByIdGenderUseCase {
    private final GenderRepository repository;

    public GetByIdGenderUseCase(GenderRepository repository) {
        this.repository = repository;
    }

    public Gender execute(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("El ID no puede ser nulo.");
        }
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Gender not found with ID: " + id));
    }
}