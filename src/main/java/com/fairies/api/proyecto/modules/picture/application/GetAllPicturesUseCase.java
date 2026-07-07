package com.fairies.api.proyecto.modules.picture.application;

import com.fairies.api.proyecto.modules.picture.domain.model.Picture;
import com.fairies.api.proyecto.modules.picture.infrastructure.persistence.PictureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class GetAllPicturesUseCase {
    private final PictureRepository repository;

    @Transactional(readOnly = true)
    public Page<Picture> execute(Pageable pageable) {
        return repository.findAll(pageable);
    }
}