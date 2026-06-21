package com.fairies.api.proyecto.modules.badge.application;

import com.fairies.api.proyecto.common.domain.service.StorageResponse;
import com.fairies.api.proyecto.common.domain.service.StorageService;
import com.fairies.api.proyecto.common.infrastructure.rest.exception.ResourceNotFoundException;
import com.fairies.api.proyecto.modules.badge.domain.model.Badge;
import com.fairies.api.proyecto.modules.badge.infrastructure.persistence.BadgeRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@Component
public class UpdateBadgeUseCase {
    private final BadgeRepository repository;
    private final StorageService storageService;

    public UpdateBadgeUseCase(BadgeRepository repository, StorageService storageService) {
        this.repository = repository;
        this.storageService = storageService;
    }

    public Badge execute(Long id, MultipartFile file, Badge updatedFields) {
        if (id == null) {
            throw new IllegalArgumentException("El ID no puede ser nulo.");
        }
        if (updatedFields == null || updatedFields.getName() == null || updatedFields.getName().isBlank()) {
            throw new IllegalArgumentException("El nombre es obligatorio.");
        }

        Badge existingBadge = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Badge with ID " + id + " not found"));

        if (file != null && !file.isEmpty()) {
            try {
                StorageResponse uploadResult = storageService.upload(file);
                existingBadge.setUrl(uploadResult.url());
                existingBadge.setPublicId(uploadResult.publicId());
            } catch (IOException e) {
                throw new RuntimeException("Error al subir la nueva imagen de la insignia a infraestructura", e);
            }
        }

        existingBadge.setName(updatedFields.getName());
        existingBadge.setDescription(updatedFields.getDescription());

        return repository.save(existingBadge);
    }
}