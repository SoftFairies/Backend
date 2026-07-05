package com.fairies.api.proyecto.modules.recommendation.application;

import com.fairies.api.proyecto.modules.book.domain.model.Book;
import com.fairies.api.proyecto.modules.book.infrastructure.persistence.BookRepository;
import com.fairies.api.proyecto.modules.library.infrastructure.persistence.LibraryRepository;
import com.fairies.api.proyecto.modules.recommendation.infrastructure.persistence.UserPreferenceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GetRecommendationUseCase {
    private final UserPreferenceRepository prefRepo;
    private final BookRepository bookRepo;
    private final LibraryRepository libRepo;

    @Transactional(readOnly = true)
    public List<Book> execute(UUID userId) {
        var pref = prefRepo.findByUserId(userId).orElseThrow();

        // 1. Obtenemos los 20 candidatos por género (DB)
        List<Book> candidates = bookRepo.findGenreMatches(
                pref.getGenres(),
                userId,
                PageRequest.of(0, 20)
        );

        if (candidates.isEmpty()) {
            return bookRepo.findAll(PageRequest.of(0, 5)).getContent(); // Fallback total
        }

        // 2. Obtenemos el formato favorito del usuario
        List<UUID> topFormatIds = libRepo.findMostUsedFormatIds(userId, PageRequest.of(0, 1));

        if (topFormatIds.isEmpty()) {
            return candidates.stream().limit(5).toList(); // No hay info de formato, regresamos géneros
        }

        UUID preferredFormatId = topFormatIds.get(0);

        // 3. "Hacemos el join" (Priorizamos los que coinciden con el formato favorito)
        // Buscamos libros que YA han sido leídos en ese formato por otros usuarios (o el mismo)
        // Si no existen en librería, se mantienen al final de la lista.
        return candidates.stream()
                .sorted((b1, b2) -> {
                    boolean match1 = libRepo.existsByBookIdAndFormatId(b1.getId(), preferredFormatId);
                    boolean match2 = libRepo.existsByBookIdAndFormatId(b2.getId(), preferredFormatId);
                    if (match1 && !match2) return -1;
                    if (!match1 && match2) return 1;
                    return 0;
                })
                .limit(5)
                .toList();
    }
}