package com.fairies.api.proyecto.modules.gamification.infrastructure.persistence;

import com.fairies.api.proyecto.modules.gamification.domain.model.UserBadge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserBadgeRepository extends JpaRepository<UserBadge, Long> {
    boolean existsByUser_IdAndBadge_Id(UUID userId, Long badgeId);
    List<UserBadge> findAllByUser_Id(UUID userId);
}