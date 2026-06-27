package com.fairies.api.proyecto.modules.catalog.infrastructure.persistence;

import com.fairies.api.proyecto.modules.catalog.domain.models.ReadingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ReadingStatusRepository extends JpaRepository<ReadingStatus, Long> {
}
