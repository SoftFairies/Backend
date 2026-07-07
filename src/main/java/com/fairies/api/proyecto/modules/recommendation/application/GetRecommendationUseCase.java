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

        List<Book> candidates = bookRepo.findGenreMatches(
                pref.getGenres(),
                userId,
                PageRequest.of(0, 20)
        );

        if (candidates.isEmpty()) {
            return bookRepo.findAll(PageRequest.of(0, 5)).getContent();
        }

        List<UUID> topFormatIds = libRepo.findMostUsedFormatIds(userId, PageRequest.of(0, 1));

        if (topFormatIds.isEmpty()) {
            return candidates.stream().limit(5).toList();
        }

        List<UUID> candidateIds = candidates.stream().map(Book::getId).toList();
        return bookRepo.findSortedByFormat(candidateIds, topFormatIds.get(0))
                .stream()
                .limit(5)
                .toList();
    }
}