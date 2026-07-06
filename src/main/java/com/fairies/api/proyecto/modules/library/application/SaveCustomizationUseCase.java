package com.fairies.api.proyecto.modules.library.application;

import com.fairies.api.proyecto.modules.library.domain.model.UserBookCustomization;
import com.fairies.api.proyecto.modules.library.domain.model.UserLibrary;
import com.fairies.api.proyecto.modules.library.infrastructure.persistence.CustomizationRepository;
import com.fairies.api.proyecto.modules.library.infrastructure.persistence.LibraryRepository;
import com.fairies.api.proyecto.modules.library.infrastructure.rest.dto.BookCustomizationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SaveCustomizationUseCase {

    private final CustomizationRepository customizationRepository;
    private final LibraryRepository libraryRepository;

    @Transactional
    public UserBookCustomization execute(UUID userId, UUID libraryId, BookCustomizationRequest request) {
        // 1. Validar que la librería existe y pertenece al usuario
        UserLibrary library = libraryRepository.findById(libraryId)
                .filter(l -> l.getUser().getId().equals(userId))
                .orElseThrow(() -> new RuntimeException("Librería no encontrada"));

        // 2. Buscar si ya hay personalización para este libro
        UserBookCustomization customization = customizationRepository
                .findByUserIdAndBookId(userId, library.getBook().getId())
                .orElse(new UserBookCustomization()); // Si no existe, preparamos una nueva

        // 3. Mapear datos (Si es nueva, asignamos referencias)
        if (customization.getId() == null) {
            customization.setUser(library.getUser());
            customization.setBook(library.getBook());
        }

        // 4. Actualizar campos
        customization.setCustomTitle(request.customTitle());
        customization.setCustomChapters(request.customChapters());
        customization.setCustomPages(request.customPages());
        customization.setCustomCoverType(request.customCoverType());
        customization.setCustomCoverValue(request.customCoverValue());

        return customizationRepository.save(customization);
    }
}