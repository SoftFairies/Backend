package com.fairies.api.proyecto.modules.badge.application;

import com.fairies.api.proyecto.common.domain.service.StorageResponse;
import com.fairies.api.proyecto.common.domain.service.StorageService;
import com.fairies.api.proyecto.modules.badge.domain.model.Badge;
import com.fairies.api.proyecto.modules.badge.infrastructure.persistence.BadgeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class AddBadgeUseCase {
    private final BadgeRepository repository;
    private final StorageService storageService;

    @Transactional
    public Badge execute(MultipartFile file, Badge badge) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("El archivo de imagen es obligatorio para la creación de la insignia.");
        }
        if (badge == null || badge.getName() == null || badge.getName().isBlank()) {
            throw new IllegalArgumentException("El nombre de la insignia es obligatorio.");
        }

        try {
            StorageResponse uploadResult = storageService.upload(file);

            badge.setUrl(uploadResult.url());
            badge.setPublicId(uploadResult.publicId());
            badge.setDeleted(false);

            return repository.save(badge);

        } catch (IOException e) {
            throw new RuntimeException("Error crítico de infraestructura al subir la imagen de la insignia", e);
        }
    }
}