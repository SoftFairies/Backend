package com.fairies.api.proyecto.modules.author.application;

import com.fairies.api.proyecto.modules.author.domain.model.Author;
import com.fairies.api.proyecto.modules.author.infrastructure.persistence.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class FindOrCreateAuthorUseCase {

    private final AuthorRepository authorRepository;

    @Transactional
    public Author execute(String name) {
        String cleanName = name.trim();
        return authorRepository.findByNameIgnoreCase(cleanName)
                .orElseGet(() -> {
                    Author newAuthor = new Author();
                    newAuthor.setName(cleanName);
                    return authorRepository.save(newAuthor);
                });
    }
}