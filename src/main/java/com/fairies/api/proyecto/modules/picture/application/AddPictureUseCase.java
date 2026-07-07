package com.fairies.api.proyecto.modules.picture.application;

import com.fairies.api.proyecto.common.domain.service.StorageResponse;
import com.fairies.api.proyecto.common.domain.service.StorageService;
import com.fairies.api.proyecto.modules.picture.domain.model.Picture;
import com.fairies.api.proyecto.modules.picture.infrastructure.persistence.PictureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class AddPictureUseCase {
    private final PictureRepository repository;
    private final StorageService storageService;

    @Transactional
    public Picture execute(MultipartFile file, Picture picture) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("El archivo de imagen es obligatorio para la creación.");
        }
        if (picture == null || picture.getName() == null || picture.getName().isBlank()) {
            throw new IllegalArgumentException("El nombre es obligatorio.");
        }

        try {
            StorageResponse uploadResult = storageService.upload(file);

            picture.setUrl(uploadResult.url());
            picture.setPublicId(uploadResult.publicId());
            picture.setDeleted(false);

            return repository.save(picture);

        } catch (IOException e) {
            throw new RuntimeException("Error crítico de infraestructura al subir la imagen", e);
        }
    }
}