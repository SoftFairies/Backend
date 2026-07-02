// feat(mailbox): implement logic to store unique content recommendations
package com.fairies.api.proyecto.modules.mailbox.application;

import com.fairies.api.proyecto.modules.mailbox.domain.model.RecommendationContent;
import com.fairies.api.proyecto.modules.mailbox.infrastructure.persistence.RecommendationContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Transactional
public class SendLetterUseCase {

    private final RecommendationContentRepository contentRepository;

    public void execute(UUID senderId, UUID bookId, String contentText) {
        contentRepository.findByBookIdAndSenderIdAndContent(bookId, senderId, contentText)
                .orElseGet(() -> contentRepository.save(
                        RecommendationContent.builder()
                                .bookId(bookId)
                                .senderId(senderId)
                                .content(contentText)
                                .build()
                ));
    }
}