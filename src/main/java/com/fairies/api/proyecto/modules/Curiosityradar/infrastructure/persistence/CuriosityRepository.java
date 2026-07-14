package com.fairies.api.proyecto.modules.Curiosityradar.infrastructure.persistence;


import com.fairies.api.proyecto.modules.Curiosityradar.domain.model.Curiosity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CuriosityRepository extends JpaRepository<Curiosity, Long> {

    // Consulta 1: Cuando el usuario sí tiene géneros favoritos establecidos
    @Query(value = "SELECT * FROM curiosities WHERE genre IN (:genres) ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    Optional<Curiosity> findRandomCuriosityByGenres(@Param("genres") List<String> genres);

    // Consulta 2: Cuando la lista de géneros está vacía y queremos cualquier curiosidad general
    @Query(value = "SELECT * FROM curiosities ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    Optional<Curiosity> findRandomCuriosity();
}
