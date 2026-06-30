package com.fairies.api.proyecto.modules.picture.application;

import com.fairies.api.proyecto.common.domain.service.StorageService;
import com.fairies.api.proyecto.common.infrastructure.rest.exception.ResourceNotFoundException;
import com.fairies.api.proyecto.modules.picture.domain.model.Picture;
import com.fairies.api.proyecto.modules.picture.infrastructure.persistence.PictureRepository;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class DeletePictureUseCase {
    private final PictureRepository repository;

    public DeletePictureUseCase(PictureRepository repository) {
        this.repository = repository;
    }

    public void execute(Long id) {
        Picture picture = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Picture with ID " + id + " not found"));
        repository.delete(picture);
    }
}