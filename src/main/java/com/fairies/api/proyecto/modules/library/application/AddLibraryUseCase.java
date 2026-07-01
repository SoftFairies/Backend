package com.fairies.api.proyecto.modules.library.application;

import com.fairies.api.proyecto.modules.book.domain.model.Book;
import com.fairies.api.proyecto.modules.book.infrastructure.persistence.BookRepository;
import com.fairies.api.proyecto.modules.library.domain.model.UserLibrary;
import com.fairies.api.proyecto.modules.library.infrastructure.persistence.LibraryRepository;
import com.fairies.api.proyecto.modules.readingStatus.domain.model.ReadingStatus;
import com.fairies.api.proyecto.modules.readingStatus.infrastructure.persistence.ReadingStatusRepository;
import com.fairies.api.proyecto.modules.user.domain.model.User;
import com.fairies.api.proyecto.modules.user.infrastructure.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class AddLibraryUseCase {

    private final LibraryRepository libraryRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final ReadingStatusRepository readingStatusRepository;

    @Transactional
    public void execute(CreateLibraryEntryCommand command) {
        User user = userRepository.findById(command.userId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Book book = bookRepository.findById(command.bookId())
                .orElseThrow(() -> new RuntimeException("Book not found"));

        ReadingStatus status = readingStatusRepository.findById(command.readingStatusId())
                .orElseThrow(() -> new RuntimeException("Status not found"));

        UserLibrary userLibrary = new UserLibrary();
        userLibrary.setUser(user);
        userLibrary.setBook(book);
        userLibrary.setReadingStatus(status);
        userLibrary.setCurrentPage(command.currentPage());

        libraryRepository.save(userLibrary);
    }
}