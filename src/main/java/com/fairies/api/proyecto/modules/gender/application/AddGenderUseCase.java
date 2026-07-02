package com.fairies.api.proyecto.modules.gender.application;

import com.fairies.api.proyecto.modules.gender.domain.model.Gender;
import com.fairies.api.proyecto.modules.gender.infrastructure.persistence.GenderRepository;
import org.springframework.stereotype.Component;

@Component
public class AddGenderUseCase {
    private final GenderRepository repository;

    public AddGenderUseCase(GenderRepository repository) {
        this.repository = repository;
    }

    public Gender execute(Gender gender) {
        if (gender == null) {
            throw new IllegalArgumentException("La entidad de género no puede ser nula.");
        }
        if (gender.getName() == null || gender.getName().isBlank()) {
            throw new IllegalArgumentException("El nombre del género es obligatorio.");
        }

        gender.setDeleted(false);
        return repository.save(gender);
    }
}