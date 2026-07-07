package com.fairies.api.proyecto.modules.Curiosityradar.aplication;

import com.fairies.api.proyecto.modules.library.domain.model.UserLibrary;
import com.fairies.api.proyecto.modules.library.infrastructure.persistence.LibraryRepository;
import com.fairies.api.proyecto.modules.Curiosityradar.domain.model.Curiosity;
import com.fairies.api.proyecto.modules.Curiosityradar.infrastructure.persistence.CuriosityRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class GetPersonalizedCuriosityUseCase {

    private final CuriosityRepository curiosityRepository;
    private final LibraryRepository libraryRepository;

    public GetPersonalizedCuriosityUseCase(CuriosityRepository curiosityRepository, LibraryRepository libraryRepository) {
        this.curiosityRepository = curiosityRepository;
        this.libraryRepository = libraryRepository;
    }

    public Curiosity execute(UUID userId) {
        if (userId == null) {
            throw new IllegalArgumentException("El ID del usuario es obligatorio.");
        }

        //Buscamos los libros del usuario en su librería
        List<UserLibrary> userLibraryEntries = libraryRepository.findAllByUserId(userId, Pageable.unpaged()).getContent();

        List<String> favoriteGenres = new java.util.ArrayList<>();

        for (UserLibrary entry : userLibraryEntries) {
            if (entry.getBook() != null && entry.getBook().getGenres() != null) {
                for (Object g : entry.getBook().getGenres()) {
                    if (g != null) {
                        String genreStr = g.toString().trim();
                        if (!genreStr.isEmpty() && !favoriteGenres.contains(genreStr)) {
                            favoriteGenres.add(genreStr);
                        }
                    }
                }
            }
        }

        //Buscamos la curiosidad al azar basándonos en si hay géneros favoritos o no
        java.util.Optional<Curiosity> result;
        if (favoriteGenres.isEmpty()) {
            // Si no tiene libros o géneros, trae cualquier curiosidad general
            result = curiosityRepository.findRandomCuriosity();
        } else {
            // Si tiene géneros favoritos
            result = curiosityRepository.findRandomCuriosityByGenres(favoriteGenres);
        }
        if (result.isEmpty()) {
            throw new RuntimeException("No se encontraron curiosidades disponibles en el Radar.");
        }
        return result.get();
    }
}