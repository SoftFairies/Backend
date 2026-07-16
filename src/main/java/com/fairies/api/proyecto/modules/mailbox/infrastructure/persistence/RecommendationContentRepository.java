package com.fairies.api.proyecto.modules.mailbox.infrastructure.persistence;

import com.fairies.api.proyecto.modules.mailbox.domain.model.RecommendationContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RecommendationContentRepository extends JpaRepository<RecommendationContent, Long> {
    Optional<RecommendationContent> findByBookIdAndSenderIdAndContent(UUID bookId, UUID senderId, String content);

    //@Query("SELECT rc FROM RecommendationContent rc WHERE rc.senderId = :senderId ORDER BY rc.id DESC")
    Optional<RecommendationContent> findFirstBySenderIdOrderByIdDesc(UUID senderId);
    long countBySenderId(UUID senderId);
}