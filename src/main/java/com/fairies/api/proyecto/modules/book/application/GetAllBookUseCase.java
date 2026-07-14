package com.fairies.api.proyecto.modules.book.application;

import com.fairies.api.proyecto.modules.book.domain.model.Book;
import com.fairies.api.proyecto.modules.book.infrastructure.persistence.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class GetAllBookUseCase {
    private final BookRepository repository;

    @Transactional(readOnly = true)
    public Page<Book> execute(String query, Pageable pageable) {
        if (query != null && !query.isBlank()) {
            return repository.searchBooks(query, pageable);
        }
        return repository.findAll(pageable);
    }
}