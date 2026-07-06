package com.fairies.api.proyecto.modules.mailbox.application;

import com.fairies.api.proyecto.modules.book.domain.model.Book;
import com.fairies.api.proyecto.modules.book.infrastructure.persistence.BookRepository;
import com.fairies.api.proyecto.modules.gamification.application.AwardBadgeUseCase;
import com.fairies.api.proyecto.modules.mailbox.domain.model.RecommendationContent;
import com.fairies.api.proyecto.modules.mailbox.infrastructure.persistence.RecommendationContentRepository;
import com.fairies.api.proyecto.modules.streak.domain.event.StreakTriggerEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SendLetterUseCase {

    private final RecommendationContentRepository contentRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final AwardBadgeUseCase awardBadgeUseCase;
    private final BookRepository bookRepository;

    @Transactional
    public void execute(UUID senderId, UUID bookId, String contentText) {
        Book book = bookRepository.getById(bookId);
        contentRepository.save(RecommendationContent.builder()
                .book(book)
                .senderId(senderId)
                .content(contentText)
                .build());
        contentRepository.flush();

        if (senderId != null) {
            awardBadgeUseCase.execute(senderId, 3L);
            eventPublisher.publishEvent(new StreakTriggerEvent(senderId));
        }
    }
}