package com.fairies.api.proyecto.modules.streak.infrastructure.event;

import com.fairies.api.proyecto.modules.streak.application.UpdateUserStreakUseCase;
import com.fairies.api.proyecto.modules.streak.domain.event.StreakTriggerEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class StreakEventListener {

    private final UpdateUserStreakUseCase updateUserStreakUseCase;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleStreakTrigger(StreakTriggerEvent event) {
        // Redirige la acción directamente al caso de uso encargado del dominio
        updateUserStreakUseCase.execute(event.userId());
    }
}