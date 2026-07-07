package com.fairies.api.proyecto.modules.gender.application;

import com.fairies.api.proyecto.modules.gender.domain.model.Gender;
import com.fairies.api.proyecto.modules.gender.infrastructure.persistence.GenderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class GetAllGenderUseCase {
    private final GenderRepository repository;

    public GetAllGenderUseCase(GenderRepository repository) {
        this.repository = repository;
    }

    public Page<Gender> execute(String query, Pageable pageable) {
        if (query != null && !query.isBlank()) {
            return repository.findByNameContainingIgnoreCase(query, pageable);
        }
        return repository.findAll(pageable);
    }
}