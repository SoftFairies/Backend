package com.fairies.api.proyecto.modules.readingsession.infrastructure.persistence;

import com.fairies.api.proyecto.modules.readingsession.domain.model.ReadingSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReadingSessionRepository extends JpaRepository<ReadingSession, UUID> {

    // Métodos de main adaptados a UUID
    List<ReadingSession> findByUserIdAndFechaBetween(UUID userId, LocalDate startDate, LocalDate endDate);

    @Query("SELECT SUM(rs.segundosLeidos) FROM ReadingSession rs WHERE rs.userId = :userId")
    Integer sumTotalSecondsByUserId(@Param("userId") UUID userId);

    // Consulta de HEAD
    @Query(value = "SELECT CAST(id AS VARCHAR) FROM books WHERE id = :id", nativeQuery = true)
    Optional<String> findBookIdNative(@Param("id") UUID id);
}