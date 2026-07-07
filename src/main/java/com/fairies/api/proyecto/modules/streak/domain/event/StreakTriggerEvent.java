package com.fairies.api.proyecto.modules.streak.domain.event;

import java.util.UUID;

public record StreakTriggerEvent(UUID userId) {
}