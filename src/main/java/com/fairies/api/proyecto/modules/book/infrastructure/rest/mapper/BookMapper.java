package com.fairies.api.proyecto.modules.book.infrastructure.rest.mapper;

import com.fairies.api.proyecto.modules.book.domain.model.Book;
import com.fairies.api.proyecto.modules.book.infrastructure.rest.dto.BookRequest;
import com.fairies.api.proyecto.modules.book.infrastructure.rest.dto.BookResponse;
import com.fairies.api.proyecto.modules.library.infrastructure.rest.dto.BookExternalSyncRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookMapper {

    @Mapping(target = "id", ignore = true)
    //@Mapping(target = "format", source = "format")
    //@Mapping(target = "authors", ignore = true)
    //@Mapping(target = "genres", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    Book toBookEntity(BookExternalSyncRequest request);

    @Mapping(target = "id", ignore = true)
    //@Mapping(target = "format", ignore = true)
    //@Mapping(target = "authors", ignore = true)
    //@Mapping(target = "genres", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "title", source = "name")
    Book toDomain(BookRequest request);

    @Mapping(target = "name", source = "title")
    BookResponse toResponse(Book book);
}