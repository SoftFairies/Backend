// feat(mailbox): add queries for user claimed history check
package com.fairies.api.proyecto.modules.mailbox.infrastructure.persistence;

import com.fairies.api.proyecto.modules.mailbox.domain.model.Letter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LetterRepository extends JpaRepository<Letter, Long> {

    long countByReceiverId(UUID receiverId);

    // Trae todas las cartas que el usuario ya ha acumulado en su buzón
    @Query("SELECT l FROM Letter l JOIN FETCH l.recommendationContent WHERE l.receiverId = :receiverId ORDER BY l.claimedAt DESC")
    List<Letter> findByReceiverIdWithContent(@Param("receiverId") UUID receiverId);
}