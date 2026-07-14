package com.fairies.api.proyecto.modules.library.application;

import com.fairies.api.proyecto.modules.format.infrastructure.persistence.FormatRepository;
import com.fairies.api.proyecto.modules.library.domain.model.UserLibrary;
import com.fairies.api.proyecto.modules.library.infrastructure.persistence.LibraryRepository;
import com.fairies.api.proyecto.modules.library.infrastructure.rest.dto.UpdateLibraryEntryRequest;
import com.fairies.api.proyecto.modules.library.infrastructure.rest.mapper.LibraryMapper;
import com.fairies.api.proyecto.modules.readingStatus.infrastructure.persistence.ReadingStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UpdateLibraryUseCase {

    private final LibraryRepository libraryRepository;
    private final ReadingStatusRepository statusRepository;
    private final FormatRepository formatRepository;
    private final LibraryMapper libraryMapper;

    @Transactional
    public UserLibrary execute(UUID userId, UUID id, UpdateLibraryEntryRequest request) {
        UserLibrary entry = libraryRepository.findById(id)
                .filter(lib -> lib.getUser().getId().equals(userId))
                .orElseThrow(() -> new IllegalArgumentException("Registro de biblioteca no encontrado o no autorizado"));

        if (request.formatId() != null) {
            entry.setFormat(formatRepository.findById(request.formatId())
                    .orElseThrow(() -> new IllegalArgumentException("Formato no encontrado")));
        }

        libraryMapper.updateFromRequest(request, entry);

        if (request.readingStatusId() != null) {
            entry.setReadingStatus(statusRepository.findById(request.readingStatusId())
                    .orElseThrow(() -> new IllegalArgumentException("Estado de lectura no encontrado")));
        }

        return libraryRepository.save(entry);
    }
}