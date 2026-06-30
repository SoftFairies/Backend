package com.fairies.api.proyecto.modules.library.application;

import com.fairies.api.proyecto.modules.book.domain.model.Book;
import com.fairies.api.proyecto.modules.book.infrastructure.persistence.BookRepository;
import com.fairies.api.proyecto.modules.library.domain.model.UserLibrary;
import com.fairies.api.proyecto.modules.library.infrastructure.persistence.LibraryRepository;
import com.fairies.api.proyecto.modules.user.domain.model.User;
import com.fairies.api.proyecto.modules.user.infrastructure.persistence.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Component
public class AddLibraryUseCase {

    private final LibraryRepository libraryRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public AddLibraryUseCase(
            LibraryRepository libraryRepository,
            BookRepository bookRepository,
            UserRepository userRepository
    ) {
        this.libraryRepository = libraryRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void execute(UserLibrary userLibrary, Book bookEntity, UUID userId) {
        // 1. Ensure the Book is persisted/found
        Book book = bookRepository.findByIsbn(bookEntity.getIsbn())
                .orElseGet(() -> bookRepository.save(bookEntity));

        // 2. Retrieve the User entity (Example: using a userRepository)
        // You must inject/use your UserRepository here
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 3. Set the full entities instead of IDs
        userLibrary.setUser(user);
        userLibrary.setBook(book);

        libraryRepository.save(userLibrary);
    }
}