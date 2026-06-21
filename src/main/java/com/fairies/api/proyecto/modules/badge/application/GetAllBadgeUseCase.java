package com.fairies.api.proyecto.modules.badge.application;

import com.fairies.api.proyecto.modules.badge.domain.model.Badge;
import com.fairies.api.proyecto.modules.badge.infrastructure.persistence.BadgeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class GetAllBadgeUseCase {
    private final BadgeRepository repository;

    public GetAllBadgeUseCase(BadgeRepository repository) {
        this.repository = repository;
    }

    public Page<Badge> execute(Pageable pageable) {
        return repository.findAll(pageable);
    }
}