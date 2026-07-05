package com.fairies.api.proyecto.modules.book.infrastructure.rest.mapper;

import com.fairies.api.proyecto.common.infrastructure.rest.GlobalMapperConfig;
import com.fairies.api.proyecto.modules.author.domain.model.Author;
import com.fairies.api.proyecto.modules.gender.domain.model.Gender;
import com.fairies.api.proyecto.modules.book.domain.model.Book;
import com.fairies.api.proyecto.modules.book.infrastructure.rest.dto.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = GlobalMapperConfig.class)
public interface BookMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    Book toDomain(CreateBookRequest request);

    BookResponse toResponse(Book book);

    default Author mapAuthorRequest(EntityReferenceRequest dto) {
        if (dto == null) return null;
        Author author = new Author();
        author.setName(dto.name());
        return author;
    }

    default Gender mapGenderRequest(EntityReferenceRequest dto) {
        if (dto == null) return null;
        Gender gender = new Gender();
        gender.setName(dto.name());
        return gender;
    }
}