package com.fairies.api.proyecto.modules.book.application;

import com.fairies.api.proyecto.modules.book.domain.model.Book;
import com.fairies.api.proyecto.modules.book.infrastructure.persistence.BookRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class GetAllBookUseCase {
    private final BookRepository repository;

    public GetAllBookUseCase(BookRepository repository) {
        this.repository = repository;
    }

    public Page<Book> execute(Pageable pageable) {
        return repository.findAll(pageable);
    }
}
