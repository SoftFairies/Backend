package com.fairies.api.proyecto.modules.gender.application;

import com.fairies.api.proyecto.common.infrastructure.rest.exception.ResourceNotFoundException;
import com.fairies.api.proyecto.modules.gender.domain.model.Gender;
import com.fairies.api.proyecto.modules.gender.infrastructure.persistence.GenderRepository;
import org.springframework.stereotype.Component;

@Component
public class DeleteGenderUseCase {
    private final GenderRepository repository;

    public DeleteGenderUseCase(GenderRepository repository) {
        this.repository = repository;
    }

    public void execute(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("El ID no puede ser nulo.");
        }
        Gender gender = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Gender cannot be deleted. ID not found: " + id));

        repository.delete(gender);
    }
}