package com.fairies.api.proyecto.modules.reports.infrastructure.rest.dto;

import java.util.List;

public record DashboardMetricsResponse(
        // Contadores superiores
        Integer estimatedMinutesTotal,
        Integer completedBooks,
        Integer annualGoal,
        Integer currentStreak,
        Integer pagesPerDayAvg,

        // Gráficas
        List<DailyMinutesDto> weeklyReadingMinutes,
        List<DailyPagesDto> monthlyPagesRead,
        LibraryDistributionDto libraryDistribution,
        List<MonthlyCompletedBooksDto> annualProgress
) {
    public record DailyMinutesDto(String day, Integer minutes) {}
    public record DailyPagesDto(Integer day, Integer pages) {}
    public record LibraryDistributionDto(Integer completed, Integer inProgress, Integer toRead) {}
    public record MonthlyCompletedBooksDto(String month, Integer books) {}
}