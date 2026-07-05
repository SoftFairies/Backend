package com.fairies.api.proyecto.modules.book.application;

import com.fairies.api.proyecto.modules.author.application.FindOrCreateAuthorUseCase;
import com.fairies.api.proyecto.modules.author.domain.model.Author;
import com.fairies.api.proyecto.modules.book.domain.model.Book;
import com.fairies.api.proyecto.modules.book.infrastructure.persistence.BookRepository;
import com.fairies.api.proyecto.modules.gender.application.FindOrCreateGenderUseCase;
import com.fairies.api.proyecto.modules.gender.domain.model.Gender;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@Transactional
@RequiredArgsConstructor
public class AddBookUseCase {

    private final BookRepository bookRepository;
    private final FindOrCreateAuthorUseCase findOrCreateAuthorUseCase;
    private final FindOrCreateGenderUseCase findOrCreateGenderUseCase;

    public Book execute(Book book) {
        if (book.getAuthors() != null) {
            book.setAuthors(book.getAuthors().stream()
                    .map(a -> findOrCreateAuthorUseCase.execute(a.getName()))
                    .collect(Collectors.toSet()));
        }

        if (book.getGenres() != null) {
            book.setGenres(book.getGenres().stream()
                    .map(g -> findOrCreateGenderUseCase.execute(g.getName()))
                    .collect(Collectors.toSet()));
        }

        return bookRepository.save(book);
    }
}