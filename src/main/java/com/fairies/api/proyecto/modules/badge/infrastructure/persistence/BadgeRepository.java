package com.fairies.api.proyecto.modules.badge.infrastructure.persistence;

import com.fairies.api.proyecto.modules.badge.domain.model.Badge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BadgeRepository extends JpaRepository<Badge, Long> {
}
