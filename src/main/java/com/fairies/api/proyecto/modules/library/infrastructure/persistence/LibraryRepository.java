package com.fairies.api.proyecto.modules.library.infrastructure.persistence;

import com.fairies.api.proyecto.modules.library.domain.model.UserLibrary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface LibraryRepository extends JpaRepository<UserLibrary, UUID> {
    Optional<UserLibrary> findByUserIdAndBookId(UUID userId, UUID bookId);
    Page<UserLibrary> findAllByUserId(UUID userId, Pageable pageable);
}
