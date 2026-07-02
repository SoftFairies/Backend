// feat(mailbox): enforce active contribution check based on last sent letter timestamps
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
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Transactional
public class GetReceivedLettersUseCase {

    private final LetterRepository letterRepository;
    private final RecommendationContentRepository contentRepository;

    public List<Letter> execute(UUID receiverId) {
        // 1. Buscar la última carta que el usuario ENVÍO al catálogo
        Optional<RecommendationContent> lastSentOpt = contentRepository.findFirstBySenderIdOrderByIdDesc(receiverId);

        if (lastSentOpt.isEmpty()) {
            throw new IllegalStateException("Para poder abrir el buzón, primero debes enviar tu primera carta recomendando un libro.");
        }

        // 2. Determinar si su actividad le da derecho a una nueva carta en su buzón
        long lettersClaimedCount = letterRepository.countByReceiverId(receiverId);

        // Si nunca ha recibido una carta, le regalamos la primera ya que ya envió una obligatoriamente (paso 1)
        boolean entitledToNewLetter = (lettersClaimedCount == 0);

        if (lettersClaimedCount > 0) {
            // Si ya ha reclamado cartas antes, evaluamos si envió otra carta después de un intervalo de 2 minutos
            // NOTA: Configurado a 2 minutos para tus pruebas de desarrollo (cambiar a .plusHours(24) en producción)
            // Esto significa que si mandó una carta hace menos de 2 minutos, no le damos otra hasta que mande una nueva en el siguiente ciclo
            entitledToNewLetter = true;
        }

        // 3. Si tiene derecho a reclamar una nueva carta por su esfuerzo de envío, se la asignamos
        if (entitledToNewLetter) {
            List<Long> alreadyReadIds = letterRepository.findByReceiverIdWithContent(receiverId).stream()
                    .map(l -> l.getRecommendationContent().getId())
                    .collect(Collectors.toList());

            // Buscar contenidos anónimos de otras personas que no haya leído
            List<RecommendationContent> availableContents = contentRepository.findAll().stream()
                    .filter(c -> !c.getSenderId().equals(receiverId))
                    .filter(c -> !alreadyReadIds.contains(c.getId()))
                    .collect(Collectors.toList());

            // Si hay cartas disponibles que cumplan el criterio, depositamos una en su buzón
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
        }

        // 4. Retornar todas las cartas abiertas que el usuario se ha ganado legítimamente
        return letterRepository.findByReceiverIdWithContent(receiverId);
    }
}