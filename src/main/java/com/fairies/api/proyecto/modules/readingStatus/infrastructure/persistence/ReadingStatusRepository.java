package com.fairies.api.proyecto.modules.readingStatus.infrastructure.persistence;

import com.fairies.api.proyecto.modules.readingStatus.domain.model.ReadingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ReadingStatusRepository extends JpaRepository<ReadingStatus, Long> {
}
