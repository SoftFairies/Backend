package com.fairies.api.proyecto.modules.library.application;

import com.fairies.api.proyecto.modules.library.domain.model.UserLibrary;
import com.fairies.api.proyecto.modules.library.infrastructure.persistence.CustomizationRepository;
import com.fairies.api.proyecto.modules.library.infrastructure.persistence.LibraryRepository;
import com.fairies.api.proyecto.modules.library.infrastructure.rest.dto.UserLibraryDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GetAllLibraryUseCase {

    private final LibraryRepository libraryRepository;

    public List<UserLibrary> execute(UUID userId, Pageable pageable) {
        return libraryRepository.findAllByUserId(userId, pageable).getContent();
    }
}