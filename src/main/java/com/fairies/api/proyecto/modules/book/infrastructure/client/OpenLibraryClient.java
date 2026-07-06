package com.fairies.api.proyecto.modules.book.infrastructure.client;

import com.fairies.api.proyecto.modules.book.infrastructure.rest.dto.CreateBookRequest;
import com.fairies.api.proyecto.modules.book.infrastructure.rest.dto.EntityReferenceRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@Primary
@RequiredArgsConstructor
public class OpenLibraryClient implements ExternalBookClient {

    private final RestTemplate restTemplate;

    private record OpenLibraryResponse(List<Doc> docs) {}

    private record Doc(
            String title,
            List<String> author_name,
            List<String> isbn,
            List<String> subject,
            List<String> subject_facet,
            Long cover_i
    ) { }

    @Override
    public List<CreateBookRequest> searchExternalBooks(String query) {
        String url = "https://openlibrary.org/search.json?q=" + query +
                "&limit=5&fields=title,author_name,isbn,subject,subject_facet,cover_i";

        List<CreateBookRequest> results = new ArrayList<>();

        try {
            var response = restTemplate.getForObject(url, OpenLibraryResponse.class);

            if (response != null && response.docs() != null) {
                for (Doc info : response.docs()) {

                    String isbn = (info.isbn() != null && !info.isbn().isEmpty())
                            ? info.isbn().get(0)
                            : "EXT-" + UUID.randomUUID().toString().substring(0, 8);

                    // Priorizamos subject_facet si existe, si no, intentamos con subject
                    List<String> subjects = (info.subject_facet() != null && !info.subject_facet().isEmpty())
                            ? info.subject_facet()
                            : info.subject();

                    Set<EntityReferenceRequest> authors = info.author_name() != null ?
                            info.author_name().stream()
                                    .map(a -> new EntityReferenceRequest(null, a))
                                    .collect(Collectors.toSet()) : null;

                    // Renombrado a 'genres' para consistencia total
                    Set<EntityReferenceRequest> genres = subjects != null ?
                            subjects.stream().limit(3)
                                    .map(c -> new EntityReferenceRequest(null, c))
                                    .collect(Collectors.toSet()) : null;

                    String cover = info.cover_i() != null ?
                            "https://covers.openlibrary.org/b/id/" + info.cover_i() + "-M.jpg" : null;

                    results.add(new CreateBookRequest(
                            isbn,
                            info.title(),
                            authors,
                            genres,
                            cover
                    ));
                }
            }
        } catch (Exception e) {
            System.err.println("Error al consumir OpenLibrary API: " + e.getMessage());
        }
        return results;
    }
}