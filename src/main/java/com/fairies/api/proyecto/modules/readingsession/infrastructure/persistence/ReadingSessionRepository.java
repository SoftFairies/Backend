package com.fairies.api.proyecto.modules.readingsession.infrastructure.persistence;


import com.fairies.api.proyecto.modules.readingsession.domain.model.ReadingSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReadingSessionRepository extends JpaRepository<ReadingSession, UUID> {
    // Consulta nativa directa a la tabla física para evitar el mapeo roto de 'format_id'
    @Query(value = "SELECT CAST(id AS VARCHAR) FROM books WHERE id = :id", nativeQuery = true)
    Optional<String> findBookIdNative(@Param("id") UUID id);
}