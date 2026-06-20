package com.fairies.api.proyecto.modules.picture.application;

import com.fairies.api.proyecto.modules.picture.domain.model.Picture;
import com.fairies.api.proyecto.modules.picture.infrastructure.persistence.PictureRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class GetPictureByIdUseCase {

    private final PictureRepository repository;

    public GetPictureByIdUseCase(PictureRepository repository) {
        this.repository = repository;
    }

    public Picture execute(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("El ID de la imagen no puede ser nulo.");
        }
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Picture with ID " + id + " not found"));
    }
}