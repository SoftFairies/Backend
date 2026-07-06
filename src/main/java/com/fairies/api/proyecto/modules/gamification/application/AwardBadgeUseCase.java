package com.fairies.api.proyecto.modules.gamification.application;

import com.fairies.api.proyecto.modules.badge.domain.model.Badge;
import com.fairies.api.proyecto.modules.badge.infrastructure.persistence.BadgeRepository;
import com.fairies.api.proyecto.modules.gamification.domain.model.UserBadge;
import com.fairies.api.proyecto.modules.gamification.infrastructure.persistence.UserBadgeRepository;
import com.fairies.api.proyecto.modules.user.domain.model.User;
import com.fairies.api.proyecto.modules.user.infrastructure.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AwardBadgeUseCase {

    private final UserBadgeRepository userBadgeRepository;
    private final UserRepository userRepository;
    private final BadgeRepository badgeRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void execute(UUID userId, Long badgeId) {
        if (!userBadgeRepository.existsByUser_IdAndBadge_Id(userId, badgeId)) {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
            Badge badge = badgeRepository.findById(badgeId)
                    .orElseThrow(() -> new IllegalArgumentException("Insignia no encontrada"));

            UserBadge userBadge = UserBadge.builder()
                    .user(user)
                    .badge(badge)
                    .build();

            userBadgeRepository.saveAndFlush(userBadge);
        }
    }
}