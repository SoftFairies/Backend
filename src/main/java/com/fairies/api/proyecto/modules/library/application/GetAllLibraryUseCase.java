package com.fairies.api.proyecto.modules.library.application;

import com.fairies.api.proyecto.modules.library.domain.model.UserBookCustomization;
import com.fairies.api.proyecto.modules.library.domain.model.UserLibrary;
import com.fairies.api.proyecto.modules.library.infrastructure.persistence.CustomizationRepository;
import com.fairies.api.proyecto.modules.library.infrastructure.persistence.LibraryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetAllLibraryUseCase {

    private final LibraryRepository libraryRepository;
    private final CustomizationRepository customizationRepository;

    // Record interno para transportar ambas entidades al controlador
    public record LibraryDetailResult(UserLibrary library, UserBookCustomization customization) {}

    @Transactional(readOnly = true)
    public Page<LibraryDetailResult> execute(UUID authenticatedUserId, Pageable pageable) {

        // 1. Obtener la página actual de la biblioteca del usuario
        Page<UserLibrary> libraryPage = libraryRepository.findAllByUserId(authenticatedUserId, pageable);

        // Si la página está vacía, no hacemos más consultas y retornamos vacío
        if (libraryPage.isEmpty()) {
            return Page.empty(pageable);
        }

        // 2. Extraer solo los IDs de los libros de esta página específica
        List<UUID> bookIds = libraryPage.getContent().stream()
                .map(lib -> lib.getBook().getId())
                .toList();

        // 3. Consultar las personalizaciones de TODOS esos libros en una sola ida a la base de datos
        List<UserBookCustomization> customizations = customizationRepository
                .findAllByUserIdAndBookIdIn(authenticatedUserId, bookIds);

        // 4. Convertir la lista a un Mapa (Diccionario) para poder enlazarlos al instante O(1)
        Map<UUID, UserBookCustomization> customMap = customizations.stream()
                .collect(Collectors.toMap(c -> c.getBook().getId(), c -> c));

        // 5. Ensamblar los resultados fusionando la librería con su personalización (o null si no tiene)
        return libraryPage.map(library ->
                new LibraryDetailResult(library, customMap.get(library.getBook().getId()))
        );
    }
}