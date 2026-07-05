package com.fairies.api.proyecto.modules.library.application;

import com.fairies.api.proyecto.modules.library.domain.model.UserLibrary;
import com.fairies.api.proyecto.modules.library.infrastructure.persistence.LibraryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetByIdLibraryUseCase {

    private final LibraryRepository libraryRepository;

    @Transactional(readOnly = true)
    public UserLibrary execute(UUID libraryId, UUID authenticatedUserId) {
        return libraryRepository.findById(libraryId)
                .filter(lib -> lib.getUser().getId().equals(authenticatedUserId))
                .orElseThrow(() -> new RuntimeException("Registro de biblioteca no encontrado o no autorizado"));
    }
}