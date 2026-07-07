package com.fairies.api.proyecto.modules.library.infrastructure.persistence;

import com.fairies.api.proyecto.modules.library.domain.model.UserLibrary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LibraryRepository extends JpaRepository<UserLibrary, UUID> {
    Optional<UserLibrary> findByUserIdAndBookId(UUID userId, UUID bookId);
    Page<UserLibrary> findAllByUserId(UUID userId, Pageable pageable);

    @Query("SELECT ul.format.id FROM UserLibrary ul " +
            "WHERE ul.user.id = :userId " +
            "GROUP BY ul.format.id ORDER BY count(ul) DESC")
    List<UUID> findMostUsedFormatIds(@Param("userId") UUID userId, Pageable pageable);
    boolean existsByBookIdAndFormatId(UUID bookId, UUID formatId);

    long countByUserId(UUID userId);

    @Query("SELECT ul.readingStatus.id, COUNT(ul) FROM UserLibrary ul WHERE ul.user.id = :userId GROUP BY ul.readingStatus.id")
    List<Object[]> countBooksByStatus(@Param("userId") UUID userId);

    @Query("SELECT ul FROM UserLibrary ul WHERE ul.user.id = :userId AND ul.finishedAt IS NOT NULL AND YEAR(ul.finishedAt) = :year")
    List<UserLibrary> findCompletedBooksByYear(@Param("userId") UUID userId, @Param("year") int year);
}
