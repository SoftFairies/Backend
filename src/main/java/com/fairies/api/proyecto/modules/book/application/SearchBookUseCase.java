package com.fairies.api.proyecto.modules.book.application;

import com.fairies.api.proyecto.modules.book.domain.model.Book;
import com.fairies.api.proyecto.modules.book.infrastructure.client.ExternalBookClient;
import com.fairies.api.proyecto.modules.book.infrastructure.persistence.BookRepository;
import com.fairies.api.proyecto.modules.book.infrastructure.rest.dto.CreateBookRequest;
import com.fairies.api.proyecto.modules.book.infrastructure.rest.mapper.BookMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SearchBookUseCase {
    private final BookRepository bookRepository;
    private final ExternalBookClient externalBookClient;
    private final BookMapper bookMapper;

    public List<Book> execute(String query) {
        List<Book> combinedResults = new ArrayList<>();
        combinedResults.addAll(bookRepository.findAllByTitleContainingIgnoreCaseOrIsbnContainingIgnoreCase(query, query));

        List<CreateBookRequest> externalData = externalBookClient.searchExternalBooks(query);

        if (externalData != null) {
            for (CreateBookRequest request : externalData) {
                Book externalBook = bookMapper.toDomain(request);
                externalBook.setId(null);
                combinedResults.add(externalBook);
            }
        }
        return combinedResults;
    }
}