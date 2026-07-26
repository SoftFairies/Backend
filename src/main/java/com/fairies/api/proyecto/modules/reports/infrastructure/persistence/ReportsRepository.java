package com.fairies.api.proyecto.modules.reports.infrastructure.persistence;

import com.fairies.api.proyecto.modules.user.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface ReportsRepository extends JpaRepository<User, UUID> {

    interface LibrarySummaryProjection {
        Integer getCompletedTotal();
        Integer getCompletedThisYear();
        Integer getInProgress();
        Integer getToRead();
    }

    interface DailyActivityProjection {
        LocalDate getDate();
        Integer getMonth();
        Integer getDayOfWeek();
        Integer getTotalSeconds();
        Integer getTotalPages();
    }

    @Query(value = "SELECT completed_total AS completedTotal, completed_this_year AS completedThisYear, in_progress AS inProgress, to_read AS toRead FROM vw_library_status_summary WHERE user_id = :userId", nativeQuery = true)
    LibrarySummaryProjection getLibrarySummary(@Param("userId") UUID userId);

    @Query(value = "SELECT date, month, day_of_week AS dayOfWeek, total_seconds AS totalSeconds, total_pages AS totalPages FROM vw_reading_sessions_daily WHERE user_id = :userId AND date >= :startDate AND date <= :endDate", nativeQuery = true)
    List<DailyActivityProjection> getDailyActivity(@Param("userId") UUID userId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query(value = "SELECT SUM(total_seconds) FROM vw_reading_sessions_daily WHERE user_id = :userId", nativeQuery = true)
    Integer sumTotalSeconds(@Param("userId") UUID userId);
}