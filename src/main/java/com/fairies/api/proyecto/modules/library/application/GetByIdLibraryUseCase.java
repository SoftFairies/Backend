package com.fairies.api.proyecto.modules.library.application;

import com.fairies.api.proyecto.modules.library.domain.model.UserBookCustomization;
import com.fairies.api.proyecto.modules.library.domain.model.UserLibrary;
import com.fairies.api.proyecto.modules.library.infrastructure.persistence.CustomizationRepository;
import com.fairies.api.proyecto.modules.library.infrastructure.persistence.LibraryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetByIdLibraryUseCase {

    private final LibraryRepository libraryRepository;
    private final CustomizationRepository customizationRepository;

    // Record interno de la capa de Aplicación para devolver ambas entidades puras de forma limpia
    public record LibraryDetailResult(UserLibrary library, UserBookCustomization customization) {}

    @Transactional(readOnly = true)
    public LibraryDetailResult execute(UUID libraryId, UUID authenticatedUserId) {

        // 1. Buscamos la relación de la biblioteca.
        // Validamos explícitamente que el registro le pertenezca al usuario del Token.
        UserLibrary library = libraryRepository.findById(libraryId)
                .filter(lib -> lib.getUser().getId().equals(authenticatedUserId))
                .orElseThrow(() -> new RuntimeException("Registro de biblioteca no encontrado o no autorizado"));
        // (Cambia el RuntimeException por la excepción global que manejes en tu proyecto)

        // 2. Buscamos si el usuario ha guardado sobreescrituras (título custom, páginas custom)
        // Si no existe, simplemente retorna null sin lanzar error.
        UserBookCustomization custom = customizationRepository
                .findByUserIdAndBookId(authenticatedUserId, library.getBook().getId())
                .orElse(null);

        // 3. Retornamos ambas entidades intactas hacia el controlador
        return new LibraryDetailResult(library, custom);
    }
}