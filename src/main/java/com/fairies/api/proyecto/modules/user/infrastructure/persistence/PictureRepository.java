package com.fairies.api.proyecto.modules.user.infrastructure.persistence;

import com.fairies.api.proyecto.modules.user.domain.model.Picture;
import com.fairies.api.proyecto.modules.user.domain.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PictureRepository extends JpaRepository<Picture, Long> {
}