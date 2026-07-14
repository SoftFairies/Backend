package com.fairies.api.proyecto.modules.library.application;

import com.fairies.api.proyecto.modules.library.domain.model.UserLibrary;
import com.fairies.api.proyecto.modules.library.infrastructure.persistence.LibraryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GetAllLibraryUseCase {

    private final LibraryRepository libraryRepository;

    @Transactional(readOnly = true)
    public Page<UserLibrary> execute(UUID userId, Pageable pageable) {
        return libraryRepository.findAllByUserId(userId, pageable);
    }
}