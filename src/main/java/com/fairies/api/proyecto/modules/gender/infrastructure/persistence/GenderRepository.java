package com.fairies.api.proyecto.modules.gender.infrastructure.persistence;

import com.fairies.api.proyecto.modules.gender.domain.model.Gender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface GenderRepository extends JpaRepository<Gender, Long> {
}
