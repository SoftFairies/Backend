package com.fairies.api.proyecto.modules.book.application;

import com.fairies.api.proyecto.modules.book.domain.model.Book;
import com.fairies.api.proyecto.modules.book.domain.model.Work;
import com.fairies.api.proyecto.modules.book.infrastructure.persistence.BookRepository;
import com.fairies.api.proyecto.modules.book.infrastructure.persistence.WorkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AddBookUseCase {

    private final BookRepository bookRepository;
    private final WorkRepository workRepository;

    @Transactional
    public Book execute(Book book) {
        // 1. Si el Work es transitorio (no tiene ID), lo persistimos primero
        Work transientWork = book.getWork();
        if (transientWork != null && transientWork.getId() == null) {
            transientWork = workRepository.save(transientWork);
            book.setWork(transientWork); // Le reasignamos el Work ya vivo con su UUID
        }

        // 2. Ahora sí, procedemos a indexar el libro tranquilamente
        if (book.getIsbn() != null && !book.getIsbn().isBlank()) {
            return bookRepository.findByIsbn(book.getIsbn())
                    .orElseGet(() -> bookRepository.save(book));
        }
        return bookRepository.save(book);
    }
}