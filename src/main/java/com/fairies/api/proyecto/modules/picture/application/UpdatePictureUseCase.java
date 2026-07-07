package com.fairies.api.proyecto.modules.picture.application;

import com.fairies.api.proyecto.common.domain.service.StorageResponse;
import com.fairies.api.proyecto.common.domain.service.StorageService;
import com.fairies.api.proyecto.common.infrastructure.rest.exception.ResourceNotFoundException;
import com.fairies.api.proyecto.modules.picture.domain.model.Picture;
import com.fairies.api.proyecto.modules.picture.infrastructure.persistence.PictureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class UpdatePictureUseCase {
    private final PictureRepository repository;
    private final StorageService storageService;

    @Transactional(readOnly = true)
    public Picture execute(Long id, MultipartFile file, Picture updatedFields) {
        if (id == null) {
            throw new IllegalArgumentException("El ID no puede ser nulo.");
        }
        if (updatedFields == null || updatedFields.getName() == null || updatedFields.getName().isBlank()) {
            throw new IllegalArgumentException("El nombre es obligatorio.");
        }

        Picture existingPicture = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Picture with ID " + id + " not found"));

        if (file != null && !file.isEmpty()) {
            try {
                StorageResponse uploadResult = storageService.upload(file);
                existingPicture.setUrl(uploadResult.url());
                existingPicture.setPublicId(uploadResult.publicId());
            } catch (IOException e) {
                throw new RuntimeException("Error al subir la nueva imagen a infraestructura", e);
            }
        }

        existingPicture.setName(updatedFields.getName());
        existingPicture.setDescription(updatedFields.getDescription());

        return repository.save(existingPicture);
    }
}