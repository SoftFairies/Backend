package com.fairies.api.proyecto.modules.book.application;

import com.fairies.api.proyecto.common.infrastructure.rest.exception.ResourceNotFoundException;
import com.fairies.api.proyecto.modules.author.infrastructure.persistence.AuthorRepository;
import com.fairies.api.proyecto.modules.book.domain.model.Book;
import com.fairies.api.proyecto.modules.book.infrastructure.persistence.BookRepository;
import com.fairies.api.proyecto.modules.format.infrastructure.persistence.FormatRepository;
import com.fairies.api.proyecto.modules.gender.infrastructure.persistence.GenderRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class UpdateBookUseCase {

    private final BookRepository bookRepository;
    private final FormatRepository formatRepository;
    private final AuthorRepository authorRepository;
    private final GenderRepository genderRepository;

    public UpdateBookUseCase(
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
    public Book execute(UUID id, Book updatedFields) {
        if (id == null) {
            throw new IllegalArgumentException("El ID no puede ser nulo.");
        }

        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Libro no encontrado con ID: " + id));

        existingBook.setIsbn(updatedFields.getIsbn());
        existingBook.setTitle(updatedFields.getTitle());
        existingBook.setDefaultChapters(updatedFields.getDefaultChapters());
        existingBook.setDefaultPages(updatedFields.getDefaultPages());
        existingBook.setOrigin(updatedFields.getOrigin());
        existingBook.setCoverType(updatedFields.getCoverType());
        existingBook.setCoverValue(updatedFields.getCoverValue());

        if (updatedFields.getFormat() != null && updatedFields.getFormat().getId() != null) {
            var format = formatRepository.findById(updatedFields.getFormat().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Formato no encontrado"));
            existingBook.setFormat(format);
        }

        if (updatedFields.getAuthors() != null) {
            var authors = updatedFields.getAuthors().stream()
                    .map(a -> authorRepository.findById(a.getId())
                            .orElseThrow(() -> new ResourceNotFoundException("Autor no encontrado con ID: " + a.getId())))
                    .collect(Collectors.toSet());
            existingBook.setAuthors(authors);
        }

        if (updatedFields.getGenres() != null) {
            var genres = updatedFields.getGenres().stream()
                    .map(g -> genderRepository.findById(g.getId())
                            .orElseThrow(() -> new ResourceNotFoundException("Género no encontrado con ID: " + g.getId())))
                    .collect(Collectors.toSet());
            existingBook.setGenres(genres);
        }

        return bookRepository.save(existingBook);
    }
}