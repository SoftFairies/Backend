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

        // Asegúrate de que este ID (ej. 3L) sea el ID de tu estado "Completado" en la tabla reading_status
        if (userLibrary.getCurrentPage() >= userLibrary.getTotalPage() ||
                userLibrary.getCurrentChapter() >= userLibrary.getTotalChapter()) {

            // 1. Usamos el repositorio correcto (ReadingStatusRepository)
            // 2. Buscamos por el ID adecuado (ej. 3L si es Long, o el UUID si es UUID)
            var completedStatus = readingStatusRepository.findById(3L)
                    .orElseThrow(() -> new ResourceNotFoundException("Status 'Completed' not found."));

            // 3. Asignamos el objeto de tipo ReadingStatus (¡No la sesión!)
            userLibrary.setReadingStatus(completedStatus);
            userLibrary.setFinishedAt(LocalDate.now());
        }


        // Lógica de Validación, para ver que el usuario no miente con su sesion
        boolean isFlagged = false;
        String flagReason = null;

        // Calcular páginas por segundo (si el tiempo es muy bajo)
        if (request.secondsRead() > 0) {
            double pagesPerSecond = (double) request.pagesRead() / request.secondsRead();

            // Si lee más de 5 páginas por segundo, es 100% trampa o error
            if (pagesPerSecond > 5.0) {
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

        if (sessionRepository.countByUserLibrary_User_Id(userId) == 0) {
            awardBadgeUseCase.execute(userId, 5L);
        }

        eventPublisher.publishEvent(new StreakTriggerEvent(userId));

        return savedSession;
    }
}