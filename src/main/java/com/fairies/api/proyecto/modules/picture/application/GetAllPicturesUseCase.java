package com.fairies.api.proyecto.modules.picture.application;

import com.fairies.api.proyecto.modules.picture.domain.model.Picture;
import com.fairies.api.proyecto.modules.picture.infrastructure.persistence.PictureRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class GetAllPicturesUseCase {
    private final PictureRepository repository;

    public GetAllPicturesUseCase(PictureRepository repository) {
        this.repository = repository;
    }

    public Page<Picture> execute(Pageable pageable) {
        return repository.findAll(pageable);
    }
}