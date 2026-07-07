package com.fairies.api.proyecto.modules.picture.application;

import com.fairies.api.proyecto.common.infrastructure.rest.exception.ResourceNotFoundException;
import com.fairies.api.proyecto.modules.picture.domain.model.Picture;
import com.fairies.api.proyecto.modules.picture.infrastructure.persistence.PictureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class GetPictureByIdUseCase {
    private final PictureRepository repository;

    @Transactional(readOnly = true)
    public Picture execute(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("El ID de la imagen no puede ser nulo.");
        }
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Picture with ID " + id + " not found"));
    }
}