package com.fairies.api.proyecto.modules.book.infrastructure.persistence;

import com.fairies.api.proyecto.modules.book.domain.model.Work;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface WorkRepository extends JpaRepository<Work, UUID> {
}