package com.fairies.api.proyecto.modules.streak.application;

import com.fairies.api.proyecto.modules.streak.domain.model.UserStreak;
import com.fairies.api.proyecto.modules.streak.infrastructure.persistence.UserStreakRepository;
import com.fairies.api.proyecto.modules.user.domain.model.User;
import com.fairies.api.proyecto.modules.user.infrastructure.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UpdateUserStreakUseCase {

    private final UserStreakRepository userStreakRepository;
    private final UserRepository userRepository;
    private final DateProvider dateProvider;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void execute(UUID userId) {
        LocalDate today = dateProvider.getToday();

        UserStreak streak = userStreakRepository.findByUserId(userId)
                .orElseGet(() -> {
                    User user = userRepository.findById(userId)
                            .orElseThrow(() -> new IllegalArgumentException("El usuario para la racha no existe."));
                    return UserStreak.builder()
                            .user(user)
                            .currentStreak(0)
                            .maxStreak(0)
                            .lastActivityDate(null)
                            .build();
                });

        // 2. Lógica matemática de control de fechas
        if (streak.getLastActivityDate() == null) {
            streak.setCurrentStreak(1);
            streak.setMaxStreak(1);
        } else if (streak.getLastActivityDate().equals(today)) {
            return;
        } else if (streak.getLastActivityDate().equals(today.minusDays(1))) {
            streak.setCurrentStreak(streak.getCurrentStreak() + 1);
            if (streak.getCurrentStreak() > streak.getMaxStreak()) {
                streak.setMaxStreak(streak.getCurrentStreak());
            }
        } else {
            streak.setCurrentStreak(1);
        }

        streak.setLastActivityDate(today);
        userStreakRepository.save(streak);
    }
}