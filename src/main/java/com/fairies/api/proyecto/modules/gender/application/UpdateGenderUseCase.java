package com.fairies.api.proyecto.modules.gender.application;

import com.fairies.api.proyecto.common.infrastructure.rest.exception.ResourceNotFoundException;
import com.fairies.api.proyecto.modules.gender.domain.model.Gender;
import com.fairies.api.proyecto.modules.gender.infrastructure.persistence.GenderRepository;
import org.springframework.stereotype.Component;

@Component
public class UpdateGenderUseCase {
    private final GenderRepository repository;

    public UpdateGenderUseCase(GenderRepository repository) {
        this.repository = repository;
    }

    public Gender execute(Long id, Gender updatedFields) {
        if (id == null) {
            throw new IllegalArgumentException("El ID no puede ser nulo.");
        }
        if (updatedFields == null || updatedFields.getName() == null || updatedFields.getName().isBlank()) {
            throw new IllegalArgumentException("El nombre del género es obligatorio para actualizar.");
        }

        Gender existingGender = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Gender not found with ID: " + id));

        existingGender.setName(updatedFields.getName());
        existingGender.setDescription(updatedFields.getDescription());

        return repository.save(existingGender);
    }
}