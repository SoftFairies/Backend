package com.fairies.api.proyecto.modules.badge.application;

import com.fairies.api.proyecto.modules.badge.domain.model.Badge;
import com.fairies.api.proyecto.modules.badge.infrastructure.persistence.BadgeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class GetAllBadgeUseCase {
    private final BadgeRepository repository;

    @Transactional(readOnly = true)
    public Page<Badge> execute(Pageable pageable) {
        return repository.findAll(pageable);
    }
}