package com.fairies.api.proyecto.modules.book.infrastructure.rest.mapper;

import com.fairies.api.proyecto.modules.book.domain.model.Book;
import com.fairies.api.proyecto.modules.book.domain.model.Work;
import com.fairies.api.proyecto.modules.catalog.domain.models.Format;
import com.fairies.api.proyecto.modules.library.infrastructure.rest.dto.BookExternalSyncRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "title", source = "request.title")
    @Mapping(target = "format", source = "format")
    @Mapping(target = "work", source = "work")
    @Mapping(target = "authors", ignore = true)
    @Mapping(target = "genres", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    Book toBookEntity(BookExternalSyncRequest request, Work work, Format format);

    @Mapping(target = "id", ignore = true)
    Work toWorkEntity(BookExternalSyncRequest request);
}