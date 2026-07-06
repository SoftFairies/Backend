package com.fairies.api.proyecto.modules.readingsession.infrastructure.persistence;


import com.fairies.api.proyecto.modules.readingsession.domain.model.ReadingSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface ReadingSessionRepository extends JpaRepository<ReadingSession, UUID> {
}