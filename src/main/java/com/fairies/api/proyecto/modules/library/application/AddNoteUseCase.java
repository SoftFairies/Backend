package com.fairies.api.proyecto.modules.library.application;

import com.fairies.api.proyecto.modules.library.domain.model.LibraryNote;
import com.fairies.api.proyecto.modules.library.domain.model.UserLibrary;
import com.fairies.api.proyecto.modules.library.infrastructure.persistence.LibraryNoteRepository;
import com.fairies.api.proyecto.modules.library.infrastructure.persistence.LibraryRepository;
import com.fairies.api.proyecto.modules.library.infrastructure.rest.dto.LibraryNoteRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AddNoteUseCase {
    private final LibraryNoteRepository noteRepository;
    private final LibraryRepository libraryRepository;

    @Transactional
    public LibraryNote execute(UUID libraryId, LibraryNoteRequest request) {
        UserLibrary library = libraryRepository.findById(libraryId).orElseThrow();

        LibraryNote note = LibraryNote.builder()
                .content(request.content())
                .chapter(request.chapter())
                .page(request.page())
                .userLibrary(library)
                .build();

        return noteRepository.save(note);
    }
}