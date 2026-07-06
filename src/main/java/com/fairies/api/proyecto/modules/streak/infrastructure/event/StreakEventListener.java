package com.fairies.api.proyecto.modules.streak.infrastructure.event;

import com.fairies.api.proyecto.modules.gamification.application.AwardBadgeUseCase;
import com.fairies.api.proyecto.modules.streak.application.GetUserStreakUseCase;
import com.fairies.api.proyecto.modules.streak.application.UpdateUserStreakUseCase;
import com.fairies.api.proyecto.modules.streak.domain.event.StreakTriggerEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class StreakEventListener {

    private final UpdateUserStreakUseCase updateUserStreakUseCase;
    private final AwardBadgeUseCase awardBadgeUseCase;
    private final GetUserStreakUseCase getUserStreakUseCase;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleStreakTrigger(StreakTriggerEvent event) {
        updateUserStreakUseCase.execute(event.userId());

        getUserStreakUseCase.execute(event.userId()).ifPresent(userStreak -> {
            int current = userStreak.getCurrentStreak();

            if (current >= 3) {
                awardBadgeUseCase.execute(event.userId(), 1L); // Racha I
            }

            if (current >= 7) {
                awardBadgeUseCase.execute(event.userId(), 6L); // Racha II
            }

            if (current >= 15) {
                awardBadgeUseCase.execute(event.userId(), 7L); // Racha III
            }
        });
    }
}