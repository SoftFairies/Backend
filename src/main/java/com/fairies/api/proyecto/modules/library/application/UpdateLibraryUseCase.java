package com.fairies.api.proyecto.modules.library.application;

import com.fairies.api.proyecto.modules.library.domain.model.UserLibrary;
import com.fairies.api.proyecto.modules.library.infrastructure.persistence.LibraryRepository;
import com.fairies.api.proyecto.modules.library.infrastructure.rest.dto.LibraryProgressRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateLibraryUseCase {

    private final LibraryRepository repository;

    @Transactional
    public void execute(UUID userId, UUID libraryId, LibraryProgressRequest request) {
        UserLibrary entity = repository.findById(libraryId)
                .filter(lib -> lib.getUser().getId().equals(userId))
                .orElseThrow(() -> new RuntimeException("Registro no encontrado o no autorizado"));

        // Actualizamos los campos necesarios
        entity.setCurrentPage(request.currentPage());
        entity.setCurrentChapter(request.currentChapter());
        // Agrega aquí otros campos que desees permitir actualizar

        repository.save(entity);
    }
}