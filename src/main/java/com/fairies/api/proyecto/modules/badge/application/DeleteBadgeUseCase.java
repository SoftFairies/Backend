package com.fairies.api.proyecto.modules.badge.application;

import com.fairies.api.proyecto.common.infrastructure.rest.exception.ResourceNotFoundException;
import com.fairies.api.proyecto.modules.badge.domain.model.Badge;
import com.fairies.api.proyecto.modules.badge.infrastructure.persistence.BadgeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteBadgeUseCase {
    private final BadgeRepository repository;

    @Transactional
    public void execute(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("El ID no puede ser nulo.");
        }
        Badge badge = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Badge cannot be deleted. ID not found: " + id));

        repository.delete(badge);
    }
}