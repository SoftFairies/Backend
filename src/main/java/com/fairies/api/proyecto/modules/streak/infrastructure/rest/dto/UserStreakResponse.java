package com.fairies.api.proyecto.modules.streak.infrastructure.rest.dto;

import java.time.LocalDate;
import java.util.UUID;

public record UserStreakResponse(
        UUID id,
        int currentStreak,
        int maxStreak,
        LocalDate lastActivityDate
) {}