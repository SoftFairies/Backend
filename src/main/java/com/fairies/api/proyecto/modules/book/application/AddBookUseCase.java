package com.fairies.api.proyecto.modules.book.application;

import com.fairies.api.proyecto.common.infrastructure.rest.exception.ResourceNotFoundException;
import com.fairies.api.proyecto.modules.author.infrastructure.persistence.AuthorRepository;
import com.fairies.api.proyecto.modules.book.domain.model.Book;
import com.fairies.api.proyecto.modules.book.infrastructure.persistence.BookRepository;
import com.fairies.api.proyecto.modules.format.infrastructure.persistence.FormatRepository;
import com.fairies.api.proyecto.modules.gender.infrastructure.persistence.GenderRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Component
public class AddBookUseCase {

    private final BookRepository bookRepository;
    private final FormatRepository formatRepository;
    private final AuthorRepository authorRepository;
    private final GenderRepository genderRepository;

    public AddBookUseCase(
            BookRepository bookRepository,
            FormatRepository formatRepository,
            AuthorRepository authorRepository,
            GenderRepository genderRepository
    ) {
        this.bookRepository = bookRepository;
        this.formatRepository = formatRepository;
        this.authorRepository = authorRepository;
        this.genderRepository = genderRepository;
    }

    @Transactional
    public Book execute(Book book) {
        if (book.getFormat() != null && book.getFormat().getId() != null) {
            var format = formatRepository.findById(book.getFormat().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Formato no encontrado"));
            book.setFormat(format);
        }

        if (book.getAuthors() != null && !book.getAuthors().isEmpty()) {
            var authors = book.getAuthors().stream()
                    .map(a -> authorRepository.findById(a.getId())
                            .orElseThrow(() -> new ResourceNotFoundException("Autor no encontrado con ID: " + a.getId())))
                    .collect(Collectors.toSet());
            book.setAuthors(authors);
        }

        if (book.getGenres() != null && !book.getGenres().isEmpty()) {
            var genres = book.getGenres().stream()
                    .map(g -> genderRepository.findById(g.getId())
                            .orElseThrow(() -> new ResourceNotFoundException("Género no encontrado con ID: " + g.getId())))
                    .collect(Collectors.toSet());
            book.setGenres(genres);
        }

        if (book.getIsbn() != null && !book.getIsbn().isBlank()) {
            return bookRepository.findByIsbn(book.getIsbn())
                    .orElseGet(() -> bookRepository.save(book));
        }

        return bookRepository.save(book);
    }
}