package com.fairies.api.proyecto.modules.library.application;

import com.fairies.api.proyecto.modules.library.domain.model.LibraryNote;
import com.fairies.api.proyecto.modules.library.infrastructure.persistence.LibraryNoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GetNotesByLibraryUseCase {
    private final LibraryNoteRepository noteRepository;

    @Transactional(readOnly = true)
    public List<LibraryNote> execute(UUID libraryId) {
        return noteRepository.findByUserLibraryIdOrderByChapterAscPageAsc(libraryId);
    }
}
