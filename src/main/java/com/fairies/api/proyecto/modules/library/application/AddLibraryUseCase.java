package com.fairies.api.proyecto.modules.library.application;

import com.fairies.api.proyecto.modules.book.application.AddBookUseCase;
import com.fairies.api.proyecto.modules.book.domain.model.Book;
import com.fairies.api.proyecto.modules.library.domain.model.UserLibrary;
import com.fairies.api.proyecto.modules.library.infrastructure.persistence.LibraryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AddLibraryUseCase {

    private final LibraryRepository libraryRepository;
    private final AddBookUseCase addBookUseCase;

    @Transactional
    public UserLibrary execute(UserLibrary userLibrary) {
        Book bookToLink = userLibrary.getBook();

        // Si el libro es Just-In-Time (no tiene ID), el módulo de libros se encarga de guardarlo y resolver dependencias
        if (bookToLink.getId() == null) {
            bookToLink = addBookUseCase.execute(bookToLink);
            userLibrary.setBook(bookToLink);
        }

        // Evitar duplicados de la estantería del usuario
        return libraryRepository.findByUserIdAndBookId(userLibrary.getUser().getId(), userLibrary.getBook().getId())
                .orElseGet(() -> libraryRepository.save(userLibrary));
    }
}