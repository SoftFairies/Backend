package com.fairies.api.proyecto.modules.mailbox.application;

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
    private final ApplicationEventPublisher eventPublisher; // <-- 1. Inyectamos el publicador de eventos

    @Transactional
    public void execute(UUID senderId, UUID bookId, String contentText) {

        contentRepository.findByBookIdAndSenderIdAndContent(bookId, senderId, contentText)
                .orElseGet(() -> contentRepository.save(
                        RecommendationContent.builder()
                                .bookId(bookId)
                                .senderId(senderId)
                                .content(contentText)
                                .build()
                ));

        if (senderId != null) {
            eventPublisher.publishEvent(new StreakTriggerEvent(senderId));
        }
    }
}