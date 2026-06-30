package com.fairies.api.proyecto.modules.picture.infrastructure.persistence;

import com.fairies.api.proyecto.modules.picture.domain.model.Picture;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PictureRepository extends JpaRepository<Picture, Long> {
    @Override
    Page<Picture> findAll(Pageable pageable);
}
