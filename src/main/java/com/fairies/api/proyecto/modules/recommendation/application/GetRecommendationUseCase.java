package com.fairies.api.proyecto.modules.recommendation.application;

import com.fairies.api.proyecto.modules.book.domain.model.Book;
import com.fairies.api.proyecto.modules.book.infrastructure.persistence.BookRepository;
import com.fairies.api.proyecto.modules.recommendation.infrastructure.persistence.UserPreferenceRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class GetRecommendationUseCase {
    private final UserPreferenceRepository prefRepo;
    private final BookRepository bookRepo;

    public GetRecommendationUseCase(UserPreferenceRepository prefRepo, BookRepository bookRepo) {
        this.prefRepo = prefRepo;
        this.bookRepo = bookRepo;
    }

    // Modificación sugerida en GetRecommendationUseCase.java
    @Transactional(readOnly = true)
    public List<Book> execute(UUID userId) {
        var pref = prefRepo.findByUserId(userId).orElseThrow();

        List<Book> recomendaciones = bookRepo.findAll().stream()
                .filter(book -> (pref.getFormats().contains(book.getFormat())) ||
                        (book.getGenres().stream().anyMatch(g -> pref.getGenres().contains(g))))
                .limit(2)
                .collect(Collectors.toList());

        return recomendaciones.isEmpty() ? bookRepo.findAll().stream().limit(2).toList() : recomendaciones;
    }
}