package com.fairies.api.proyecto.modules.readingsession.aplication;

import com.fairies.api.proyecto.common.infrastructure.rest.exception.ResourceNotFoundException;
import com.fairies.api.proyecto.modules.gamification.application.AwardBadgeUseCase;
import com.fairies.api.proyecto.modules.library.domain.model.UserLibrary;
import com.fairies.api.proyecto.modules.library.infrastructure.persistence.LibraryRepository;
import com.fairies.api.proyecto.modules.readingStatus.infrastructure.persistence.ReadingStatusRepository;
import com.fairies.api.proyecto.modules.readingsession.domain.model.ReadingSession;
import com.fairies.api.proyecto.modules.readingsession.infrastructure.persistence.ReadingSessionRepository;
import com.fairies.api.proyecto.modules.readingsession.infrastructure.rest.dto.ReadingSessionRequest;
import com.fairies.api.proyecto.modules.streak.domain.event.StreakTriggerEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AddReadingSessionUseCase {

    private final ReadingSessionRepository sessionRepository;
    private final LibraryRepository libraryRepository;
    private final ReadingStatusRepository readingStatusRepository;
    private final AwardBadgeUseCase awardBadgeUseCase;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public ReadingSession execute(UUID userId, UUID libraryId, ReadingSessionRequest request) {

        UserLibrary userLibrary = libraryRepository.findById(libraryId)
                .filter(lib -> lib.getUser().getId().equals(userId))
                .orElseThrow(() -> new ResourceNotFoundException("Library not found."));

        userLibrary.setCurrentPage(userLibrary.getCurrentPage() + request.pagesRead());
        userLibrary.setCurrentChapter(userLibrary.getCurrentChapter() + request.chaptersRead());

        if (userLibrary.getCurrentPage() >= userLibrary.getTotalPage() ||
                userLibrary.getCurrentChapter() >= userLibrary.getTotalChapter()) {

            var completedStatus = readingStatusRepository.findById(3L)
                    .orElseThrow(() -> new ResourceNotFoundException("Status 'Completed' not found."));

            userLibrary.setReadingStatus(completedStatus);
            userLibrary.setFinishedAt(LocalDate.now());
        }


        boolean isFlagged = false;
        String flagReason = null;

        if (request.secondsRead() > 0) {
            double pagesPerSecond = (double) request.pagesRead() / request.secondsRead();

            if (pagesPerSecond > 5.0 ) {
                isFlagged = true;
                flagReason = "Velocidad inhumana: " + String.format("%.2f", pagesPerSecond) + " pág/seg";
            }
        }

        ReadingSession session = ReadingSession.builder()
                .userLibrary(userLibrary)
                .date((request.date() != null) ? request.date() : LocalDate.now())
                .secondsRead(request.secondsRead())
                .pagesRead(request.pagesRead())
                .chaptersRead(request.chaptersRead())
                .isFlagged(isFlagged)
                .flagReason(flagReason)
                .build();

        ReadingSession savedSession = sessionRepository.save(session);

        sessionRepository.flush();

        if (sessionRepository.countByUserLibrary_User_Id(userId) == 1) {

            awardBadgeUseCase.execute(userId, 5L);
        }

        eventPublisher.publishEvent(new StreakTriggerEvent(userId));

        return savedSession;
    }
}