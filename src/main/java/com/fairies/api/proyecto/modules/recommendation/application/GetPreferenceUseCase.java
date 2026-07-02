package com.fairies.api.proyecto.modules.recommendation.application;

import com.fairies.api.proyecto.modules.recommendation.domain.model.UserPreference;
import com.fairies.api.proyecto.modules.recommendation.infrastructure.persistence.UserPreferenceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GetPreferenceUseCase {
    private final UserPreferenceRepository prefRepo;

    @Transactional(readOnly = true)
    public UserPreference execute(UUID userId) {
        return prefRepo.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Preferencias no encontradas"));
    }
}