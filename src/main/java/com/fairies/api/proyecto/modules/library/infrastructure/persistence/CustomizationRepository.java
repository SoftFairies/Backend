package com.fairies.api.proyecto.modules.library.infrastructure.persistence;

import com.fairies.api.proyecto.modules.library.domain.model.UserBookCustomization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomizationRepository extends JpaRepository<UserBookCustomization, UUID> {
    Optional<UserBookCustomization> findByUserIdAndBookId(UUID userId, UUID bookId);
    List<UserBookCustomization> findAllByUserIdAndBookIdIn(UUID userId, List<UUID> bookIds);
}