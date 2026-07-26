package com.fairies.api.proyecto.modules.reports.infrastructure.rest.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record AdminResponseDto(
        Long totalUsers,
        Long activeToday,
        Long trackedBooks,
        Long sessionsToday,
        Long completedBooks,
        Long avgReadingMinutesDay,
        List<UserAdminItemDto> users
) {
    public record UserAdminItemDto(
            UUID userId,
            String name,
            String email,
            Long books,
            Long sessions,
            Integer streak,
            LocalDate lastActiveDate,
            String status
    ) {}
}