package com.fairies.api.proyecto.modules.library.application;

import com.fairies.api.proyecto.common.infrastructure.rest.exception.ResourceNotFoundException;
import com.fairies.api.proyecto.modules.library.domain.model.UserLibrary;
import com.fairies.api.proyecto.modules.library.infrastructure.persistence.LibraryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DeleteLibraryUseCase {
    private final LibraryRepository repository;

    @Transactional
    public void execute(UUID userId, UUID libraryId) {
        UserLibrary entity = repository.findById(libraryId)
                .filter(lib -> lib.getUser().getId().equals(userId))
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró la entrada en la librería o no tienes permiso."));

        repository.delete(entity);
    }
}
