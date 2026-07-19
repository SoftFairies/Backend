package com.fairies.api.proyecto.modules.mailbox.infrastructure.persistence;

import com.fairies.api.proyecto.modules.mailbox.domain.model.RecommendationContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RecommendationContentRepository extends JpaRepository<RecommendationContent, Long> {

    Optional<RecommendationContent> findByBookIdAndSenderIdAndContent(UUID bookId, UUID senderId, String content);

    Optional<RecommendationContent> findFirstBySenderIdOrderByIdDesc(UUID senderId);

    long countBySenderId(UUID senderId);

    @Query("SELECT rc FROM RecommendationContent rc WHERE rc.senderId != :receiverId AND rc.id NOT IN (SELECT l.recommendationContent.id FROM Letter l WHERE l.receiverId = :receiverId)")
    List<RecommendationContent> findAvailableForReceiver(@Param("receiverId") UUID receiverId);
}