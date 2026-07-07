package com.fairies.api.proyecto.modules.reports.application;

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
@Transactional(readOnly = true)
public class GetDashboardMetricsUseCase {

    private final UserRepository userRepository;
    private final LibraryRepository libraryRepository;
    private final ReadingSessionRepository sessionRepository;
    private final GetUserStreakUseCase getUserStreakUseCase;

    public DashboardMetricsResponse execute(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow();
        LocalDate today = LocalDate.now();

        // 1. Minutos Totales
        Integer totalSeconds = sessionRepository.sumTotalSecondsByUserId(userId);
        int totalMinutes = (totalSeconds != null) ? totalSeconds / 60 : 0;

        // 2. Racha actual
        int currentStreak = getUserStreakUseCase.execute(userId)
                .map(streak -> streak.getCurrentStreak())
                .orElse(0);

        // 3. Distribución de Biblioteca (Asumiendo IDs: 1=Por Leer, 2=Leyendo, 3=Completado. ¡Cámbialos según tu BD!)
        List<Object[]> statusCounts = libraryRepository.countBooksByStatus(userId);
        int toRead = 0, inProgress = 0, completed = 0;
        for (Object[] row : statusCounts) {
            Long statusId = (Long) row[0];
            Long count = (Long) row[1];
            if (statusId == 1L) toRead = count.intValue();
            if (statusId == 2L) inProgress = count.intValue();
            if (statusId == 3L) completed = count.intValue();
        }

        // 4. Progreso Anual (Ene - Dic)
        List<UserLibrary> completedThisYear = libraryRepository.findCompletedBooksByYear(userId, today.getYear());
        Map<Month, Long> booksByMonth = completedThisYear.stream()
                .collect(Collectors.groupingBy(lib -> lib.getFinishedAt().getMonth(), Collectors.counting()));

        List<MonthlyCompletedBooksDto> annualProgress = Arrays.stream(Month.values())
                .map(month -> new MonthlyCompletedBooksDto(getSpanishMonth(month), booksByMonth.getOrDefault(month, 0L).intValue()))
                .toList();

        // 5. Sesiones de Lectura (Cálculos de gráfica semanal y mensual)
        LocalDate startOfMonth = today.with(TemporalAdjusters.firstDayOfMonth());
        List<ReadingSession> monthlySessions = sessionRepository.findByUserIdAndFechaBetween(userId, startOfMonth, today);

        // -- Páginas promedio por día (del mes actual)
        int totalPagesThisMonth = monthlySessions.stream().mapToInt(ReadingSession::getPaginasAvanzadas).sum();
        int pagesPerDayAvg = totalPagesThisMonth / Math.max(1, today.getDayOfMonth());

        // -- Gráfica: Páginas leídas por día (Mes)
        Map<Integer, Integer> pagesByDay = monthlySessions.stream()
                .collect(Collectors.groupingBy(s -> s.getFecha().getDayOfMonth(), Collectors.summingInt(ReadingSession::getPaginasAvanzadas)));
        List<DailyPagesDto> monthlyPagesRead = new ArrayList<>();
        for (int i = 1; i <= today.lengthOfMonth(); i++) {
            monthlyPagesRead.add(new DailyPagesDto(i, pagesByDay.getOrDefault(i, 0)));
        }

        // -- Gráfica: Minutos por día (Semana actual)
        LocalDate startOfWeek = today.with(DayOfWeek.MONDAY);
        Map<DayOfWeek, Integer> secondsByDayOfWeek = monthlySessions.stream()
                .filter(s -> !s.getFecha().isBefore(startOfWeek))
                .collect(Collectors.groupingBy(s -> s.getFecha().getDayOfWeek(), Collectors.summingInt(ReadingSession::getSegundosLeidos)));

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