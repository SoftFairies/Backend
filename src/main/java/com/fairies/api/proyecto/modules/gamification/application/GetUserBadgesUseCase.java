package com.fairies.api.proyecto.modules.gamification.application;

import com.fairies.api.proyecto.modules.gamification.domain.model.UserBadge;
import com.fairies.api.proyecto.modules.gamification.infrastructure.persistence.UserBadgeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GetUserBadgesUseCase {

    private final UserBadgeRepository userBadgeRepository;

    @Transactional(readOnly = true)
    public List<UserBadge> execute(UUID userId) {
        return userBadgeRepository.findAllByUser_Id(userId);
    }
}