package com.fairies.api.proyecto.modules.readingsession.infrastructure.persistence;

import com.fairies.api.proyecto.modules.readingsession.domain.model.ReadingSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface ReadingSessionRepository extends JpaRepository<ReadingSession, UUID> {

    List<ReadingSession> findByUserLibrary_User_IdAndDateBetween(UUID userId, LocalDate startDate, LocalDate endDate);

    long countByUserLibrary_User_Id(UUID userId);

    @Query("SELECT SUM(rs.secondsRead) FROM ReadingSession rs WHERE rs.userLibrary.user.id = :userId")
    Integer sumTotalSecondsByUserId(@Param("userId") UUID userId);
}