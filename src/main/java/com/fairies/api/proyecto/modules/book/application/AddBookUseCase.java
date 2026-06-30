package com.fairies.api.proyecto.modules.book.application;

import com.fairies.api.proyecto.modules.book.domain.model.Book;
import com.fairies.api.proyecto.modules.book.infrastructure.persistence.BookRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class AddBookUseCase {

    private final BookRepository bookRepository;

    public AddBookUseCase(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Transactional
    public Book execute(Book book) {
        if (book.getIsbn() != null && !book.getIsbn().isBlank()) {
            return bookRepository.findByIsbn(book.getIsbn())
                    .orElseGet(() -> bookRepository.save(book));
        }
        return bookRepository.save(book);
    }
}