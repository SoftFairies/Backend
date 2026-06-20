package com.fairies.api.proyecto.modules.picture.application;

import com.fairies.api.proyecto.common.domain.service.StorageResponse;
import com.fairies.api.proyecto.common.domain.service.StorageService;
import com.fairies.api.proyecto.modules.picture.domain.model.Picture;
import com.fairies.api.proyecto.modules.picture.infrastructure.persistence.PictureRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@Component
public class AddPictureUseCase {
    private final PictureRepository repository;
    private final StorageService storageService;

    public AddPictureUseCase(PictureRepository repository, StorageService storageService) {
        this.repository = repository;
        this.storageService = storageService;
    }

    public Picture execute(MultipartFile file, String name) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("El archivo de imagen es mandatorio y no puede estar vacío.");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("El nombre del catálogo es obligatorio.");
        }

        try {
            StorageResponse uploadResult = storageService.upload(file);

            Picture picture = Picture.builder()
                    .name(name)
                    .url(uploadResult.url())
                    .publicId(uploadResult.publicId())
                    .deleted(false)
                    .build();

            return repository.save(picture);

        } catch (IOException e) {
            throw new RuntimeException("Error crítico de infraestructura al subir la imagen", e);
        }
    }
}