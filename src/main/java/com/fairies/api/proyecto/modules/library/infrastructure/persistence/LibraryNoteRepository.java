package com.fairies.api.proyecto.modules.library.infrastructure.persistence;

import com.fairies.api.proyecto.modules.library.domain.model.LibraryNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LibraryNoteRepository extends JpaRepository<LibraryNote, Long> {
    List<LibraryNote> findByUserLibraryIdOrderByChapterAscPageAsc(UUID userLibraryId);
}