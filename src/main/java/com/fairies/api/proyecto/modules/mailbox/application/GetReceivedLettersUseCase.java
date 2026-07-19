package com.fairies.api.proyecto.modules.mailbox.application;

import com.fairies.api.proyecto.modules.mailbox.domain.model.Letter;
import com.fairies.api.proyecto.modules.mailbox.domain.model.RecommendationContent;
import com.fairies.api.proyecto.modules.mailbox.infrastructure.persistence.LetterRepository;
import com.fairies.api.proyecto.modules.mailbox.infrastructure.persistence.RecommendationContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GetReceivedLettersUseCase {

    private final LetterRepository letterRepository;
    private final RecommendationContentRepository contentRepository;

    @Transactional
    public List<Letter> execute(UUID receiverId) {
        Optional<RecommendationContent> lastSentOpt = contentRepository.findFirstBySenderIdOrderByIdDesc(receiverId);
        if (lastSentOpt.isEmpty()) {
            throw new IllegalStateException("Para poder abrir el buzón, primero debes enviar tu primera carta recomendando un libro.");
        }

        List<RecommendationContent> availableContents = contentRepository.findAvailableForReceiver(receiverId);

        if (!availableContents.isEmpty()) {
            Collections.shuffle(availableContents);
            RecommendationContent selectedContent = availableContents.get(0);

            Letter newLetter = Letter.builder()
                    .receiverId(receiverId)
                    .recommendationContent(selectedContent)
                    .claimedAt(LocalDateTime.now())
                    .build();

            letterRepository.save(newLetter);
        }

        return letterRepository.findByReceiverIdWithContent(receiverId);
    }
}