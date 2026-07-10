package com.fairies.api.proyecto.modules.reports.application;

import com.fairies.api.proyecto.common.infrastructure.rest.exception.ResourceNotFoundException;
import com.fairies.api.proyecto.modules.library.domain.model.UserLibrary;
import com.fairies.api.proyecto.modules.library.infrastructure.persistence.LibraryRepository;
import com.fairies.api.proyecto.modules.readingsession.domain.model.ReadingSession;
import com.fairies.api.proyecto.modules.readingsession.infrastructure.persistence.ReadingSessionRepository;
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
    private final LibraryRepository libraryRepository;
    private final ReadingSessionRepository sessionRepository;
    private final GetUserStreakUseCase getUserStreakUseCase;

    @Transactional(readOnly = true)
    public DashboardMetricsResponse execute(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado."));

        LocalDate today = LocalDate.now();

        Integer totalSeconds = sessionRepository.sumTotalSecondsByUserId(userId);
        int totalMinutes = (totalSeconds != null) ? totalSeconds / 60 : 0;

        int currentStreak = getUserStreakUseCase.execute(userId)
                .map(streak -> streak.getCurrentStreak())
                .orElse(0);

        List<Object[]> statusCounts = libraryRepository.countBooksByStatus(userId);
        int toRead = 0, inProgress = 0, completed = 0, other= 0;
        for (Object[] row : statusCounts) {
            Long statusId = (Long) row[0];
            Long count = (Long) row[1];
            if (statusId == 5L) toRead = count.intValue();
            else if (statusId == 2L) completed = count.intValue();
            else other += count.intValue();
        }

        List<UserLibrary> completedThisYear = libraryRepository.findCompletedBooksByYear(userId, today.getYear());
        Map<Month, Long> booksByMonth = completedThisYear.stream()
                .filter(lib -> lib.getFinishedAt() != null)
                .collect(Collectors.groupingBy(lib -> lib.getFinishedAt().getMonth(), Collectors.counting()));

        List<MonthlyCompletedBooksDto> annualProgress = Arrays.stream(Month.values())
                .map(month -> new MonthlyCompletedBooksDto(getSpanishMonth(month), booksByMonth.getOrDefault(month, 0L).intValue()))
                .toList();

        LocalDate startOfMonth = today.with(TemporalAdjusters.firstDayOfMonth());

        List<ReadingSession> monthlySessions = sessionRepository.findByUserLibrary_User_IdAndDateBetween(userId, startOfMonth, today);

        int totalPagesThisMonth = monthlySessions.stream().mapToInt(ReadingSession::getPagesRead).sum();
        int pagesPerDayAvg = totalPagesThisMonth / Math.max(1, today.getDayOfMonth());

        Map<Integer, Integer> pagesByDay = monthlySessions.stream()
                .collect(Collectors.groupingBy(s -> s.getDate().getDayOfMonth(), Collectors.summingInt(ReadingSession::getPagesRead)));
        List<DailyPagesDto> monthlyPagesRead = new ArrayList<>();
        for (int i = 1; i <= today.lengthOfMonth(); i++) {
            monthlyPagesRead.add(new DailyPagesDto(i, pagesByDay.getOrDefault(i, 0)));
        }

        LocalDate startOfWeek = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

        Map<DayOfWeek, Integer> secondsByDayOfWeek = monthlySessions.stream()
                .filter(s -> !s.getDate().isBefore(startOfWeek))
                .collect(Collectors.groupingBy(s -> s.getDate().getDayOfWeek(), Collectors.summingInt(ReadingSession::getSecondsRead)));

        List<DailyMinutesDto> weeklyMinutes = Arrays.stream(DayOfWeek.values())
                .map(day -> new DailyMinutesDto(getSpanishDay(day), secondsByDayOfWeek.getOrDefault(day, 0) / 60))
                .toList();

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