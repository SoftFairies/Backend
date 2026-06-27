package com.fairies.api.proyecto.modules.catalog.infrastructure.persistence;

import com.fairies.api.proyecto.modules.catalog.domain.models.Gender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface GenderRepository extends JpaRepository<Gender, Long> {
}
