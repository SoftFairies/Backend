package com.fairies.api.proyecto.modules.streak.application;

import com.fairies.api.proyecto.modules.streak.domain.model.UserStreak;
import com.fairies.api.proyecto.modules.streak.infrastructure.persistence.UserStreakRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GetUserStreakUseCase {

    private final UserStreakRepository userStreakRepository;

    public Optional<UserStreak> execute(UUID userId) {
        return userStreakRepository.findByUserId(userId);
    }
}