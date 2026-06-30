package com.fairies.api.proyecto.modules.badge.application;

import com.fairies.api.proyecto.common.infrastructure.rest.exception.ResourceNotFoundException;
import com.fairies.api.proyecto.modules.badge.domain.model.Badge;
import com.fairies.api.proyecto.modules.badge.infrastructure.persistence.BadgeRepository;
import org.springframework.stereotype.Component;

@Component
public class GetByIdBadgeUseCase {
    private final BadgeRepository repository;

    public GetByIdBadgeUseCase(BadgeRepository repository) {
        this.repository = repository;
    }

    public Badge execute(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("El ID no puede ser nulo.");
        }
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Badge not found with ID: " + id));
    }
}