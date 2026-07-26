package com.fairies.api.proyecto.modules.reports.infrastructure.persistence;

import com.fairies.api.proyecto.modules.user.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface AdminRepository extends JpaRepository<User, UUID> {

    interface AdminMetricsProjection {
        Long getTotalUsers();
        Long getActiveToday();
        Long getTrackedBooks();
        Long getSessionsToday();
        Long getCompletedBooks();
        Long getAvgReadingMinutesDay();
    }

    interface AdminUserListProjection {
        UUID getUserId();
        String getName();
        String getEmail();
        Long getBooksCount();
        Long getSessionsCount();
        Integer getStreak();
        LocalDate getLastActiveDate();
        String getStatus();
    }

    @Query(value = "SELECT total_users AS totalUsers, active_today AS activeToday, tracked_books AS trackedBooks, sessions_today AS sessionsToday, completed_books AS completedBooks, avg_reading_minutes_day AS avgReadingMinutesDay FROM vw_admin_metrics_summary", nativeQuery = true)
    AdminMetricsProjection getAdminMetrics();

    @Query(value = "SELECT user_id AS userId, name, email, books_count AS booksCount, sessions_count AS sessionsCount, streak, last_active_date AS lastActiveDate, status FROM vw_admin_users_list ORDER BY streak DESC, books_count DESC", nativeQuery = true)
    List<AdminUserListProjection> getAdminUsersList();
}
