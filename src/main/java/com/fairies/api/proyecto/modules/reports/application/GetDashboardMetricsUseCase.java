package com.fairies.api.proyecto.modules.reports.application;

import com.fairies.api.proyecto.common.infrastructure.rest.exception.ResourceNotFoundException;
import com.fairies.api.proyecto.modules.reports.infrastructure.persistence.ReportsRepository;
import com.fairies.api.proyecto.modules.reports.infrastructure.rest.dto.DashboardMetricsResponse;
import com.fairies.api.proyecto.modules.reports.infrastructure.rest.dto.DashboardMetricsResponse.*;
import com.fairies.api.proyecto.modules.streak.application.GetUserStreakUseCase;
import com.fairies.api.proyecto.modules.user.domain.model.User;
import com.fairies.api.proyecto.modules.user.infrastructure.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GetDashboardMetricsUseCase {

    private final UserRepository userRepository;
    private final ReportsRepository reportsRepository;
    private final GetUserStreakUseCase getUserStreakUseCase;

    @Transactional(readOnly = true)
    public DashboardMetricsResponse execute(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado."));

        LocalDate today = LocalDate.now();

        Integer totalSeconds = reportsRepository.sumTotalSeconds(userId);
        int totalMinutes = (totalSeconds != null) ? totalSeconds / 60 : 0;

        int currentStreak = getUserStreakUseCase.execute(userId)
                .map(streak -> streak.getCurrentStreak())
                .orElse(0);

        ReportsRepository.LibrarySummaryProjection summary = reportsRepository.getLibrarySummary(userId);
        int completed = (summary != null && summary.getCompletedTotal() != null) ? summary.getCompletedTotal() : 0;
        int inProgress = (summary != null && summary.getInProgress() != null) ? summary.getInProgress() : 0;
        int toRead = (summary != null && summary.getToRead() != null) ? summary.getToRead() : 0;

        LocalDate startOfMonth = today.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate startOfWeek = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

        List<ReportsRepository.DailyActivityProjection> monthlyActivity =
                reportsRepository.getDailyActivity(userId, startOfMonth, today);

        int totalPagesThisMonth = monthlyActivity.stream()
                .mapToInt(a -> a.getTotalPages() != null ? a.getTotalPages() : 0)
                .sum();
        int pagesPerDayAvg = totalPagesThisMonth / Math.max(1, today.getDayOfMonth());

        Map<Integer, Integer> pagesByDay = monthlyActivity.stream()
                .collect(Collectors.toMap(
                        a -> a.getDate().getDayOfMonth(),
                        a -> a.getTotalPages() != null ? a.getTotalPages() : 0,
                        Integer::sum
                ));

        List<DailyPagesDto> monthlyPagesRead = new ArrayList<>();
        for (int i = 1; i <= today.lengthOfMonth(); i++) {
            monthlyPagesRead.add(new DailyPagesDto(i, pagesByDay.getOrDefault(i, 0)));
        }

        Map<DayOfWeek, Integer> secondsByDayOfWeek = monthlyActivity.stream()
                .filter(a -> !a.getDate().isBefore(startOfWeek))
                .collect(Collectors.toMap(
                        a -> a.getDate().getDayOfWeek(),
                        a -> a.getTotalSeconds() != null ? a.getTotalSeconds() : 0,
                        Integer::sum
                ));

        List<DailyMinutesDto> weeklyMinutes = Arrays.stream(DayOfWeek.values())
                .map(day -> new DailyMinutesDto(getSpanishDay(day), secondsByDayOfWeek.getOrDefault(day, 0) / 60))
                .toList();

        List<MonthlyCompletedBooksDto> annualProgress = Collections.emptyList(); // O tu lógica optimizada

        return new DashboardMetricsResponse(
                totalMinutes,
                completed,
                user.getAnnualGoal() != null ? user.getAnnualGoal() : 0,
                currentStreak,
                pagesPerDayAvg,
                weeklyMinutes,
                monthlyPagesRead,
                new LibraryDistributionDto(completed, inProgress, toRead),
                annualProgress
        );
    }

    private String getSpanishMonth(Month month) {
        String[] meses = {"Ene", "Feb", "Mar", "Abr", "May", "Jun", "Jul", "Ago", "Sep", "Oct", "Nov", "Dic"};
        return meses[month.ordinal()];
    }

    private String getSpanishDay(DayOfWeek day) {
        String[] dias = {"Lun", "Mar", "Mié", "Jue", "Vie", "Sáb", "Dom"};
        return dias[day.ordinal()];
    }
}