package com.fairies.api.proyecto.modules.library.application;

import com.fairies.api.proyecto.modules.library.domain.model.UserLibrary;
import com.fairies.api.proyecto.modules.library.infrastructure.persistence.LibraryRepository;
import com.fairies.api.proyecto.modules.library.infrastructure.rest.dto.UpdateLibraryEntryRequest;
import com.fairies.api.proyecto.modules.readingStatus.infrastructure.persistence.ReadingStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Component
@Transactional
@RequiredArgsConstructor
public class UpdateLibraryUseCase {
    private final LibraryRepository libraryRepository;
    private final ReadingStatusRepository statusRepository;

    public void execute(UUID userId, UUID id, UpdateLibraryEntryRequest request) {
        UserLibrary entry = libraryRepository.findById(id).orElseThrow();

        if (request.readingStatusId() != null)
            entry.setReadingStatus(statusRepository.findById(request.readingStatusId()).orElseThrow());
        if (request.currentChapter() != null) entry.setCurrentChapter(request.currentChapter());
        if (request.currentPage() != null) entry.setCurrentPage(request.currentPage());
        if (request.isFavorite() != null) entry.setFavorite(request.isFavorite());

        libraryRepository.save(entry);
    }
}