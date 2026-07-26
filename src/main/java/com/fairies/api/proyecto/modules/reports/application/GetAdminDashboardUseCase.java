package com.fairies.api.proyecto.modules.reports.application;

import com.fairies.api.proyecto.modules.reports.infrastructure.persistence.AdminRepository;
import com.fairies.api.proyecto.modules.reports.infrastructure.rest.dto.AdminResponseDto;
import com.fairies.api.proyecto.modules.reports.infrastructure.rest.dto.AdminResponseDto.UserAdminItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GetAdminDashboardUseCase {

    private final AdminRepository adminRepository;

    @Transactional(readOnly = true)
    public AdminResponseDto execute() {
        var metrics = adminRepository.getAdminMetrics();
        var usersProjection = adminRepository.getAdminUsersList();

        List<UserAdminItemDto> userList = usersProjection.stream()
                .map(u -> new UserAdminItemDto(
                        u.getUserId(),
                        u.getName(),
                        u.getEmail(),
                        u.getBooksCount(),
                        u.getSessionsCount(),
                        u.getStreak(),
                        u.getLastActiveDate(),
                        u.getStatus()
                )).toList();

        return new AdminResponseDto(
                metrics != null && metrics.getTotalUsers() != null ? metrics.getTotalUsers() : 0L,
                metrics != null && metrics.getActiveToday() != null ? metrics.getActiveToday() : 0L,
                metrics != null && metrics.getTrackedBooks() != null ? metrics.getTrackedBooks() : 0L,
                metrics != null && metrics.getSessionsToday() != null ? metrics.getSessionsToday() : 0L,
                metrics != null && metrics.getCompletedBooks() != null ? metrics.getCompletedBooks() : 0L,
                metrics != null && metrics.getAvgReadingMinutesDay() != null ? metrics.getAvgReadingMinutesDay() : 0L,
                userList
        );
    }
}
