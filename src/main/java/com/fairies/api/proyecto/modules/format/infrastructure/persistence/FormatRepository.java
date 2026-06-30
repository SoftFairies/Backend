package com.fairies.api.proyecto.modules.format.infrastructure.persistence;

import com.fairies.api.proyecto.modules.format.domain.model.Format;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FormatRepository extends JpaRepository<Format, Long> {
}
