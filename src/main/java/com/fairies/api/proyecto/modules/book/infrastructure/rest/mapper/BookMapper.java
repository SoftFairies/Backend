package com.fairies.api.proyecto.modules.book.infrastructure.rest.mapper;

import com.fairies.api.proyecto.modules.author.domain.model.Author;
import com.fairies.api.proyecto.modules.author.infrastructure.rest.mapper.AuthorMapper;
import com.fairies.api.proyecto.modules.book.domain.model.Book;
import com.fairies.api.proyecto.modules.book.infrastructure.rest.dto.BookRequest;
import com.fairies.api.proyecto.modules.book.infrastructure.rest.dto.BookResponse;
import com.fairies.api.proyecto.modules.format.domain.model.Format;
import com.fairies.api.proyecto.modules.format.infrastructure.rest.mapper.FormatMapper;
import com.fairies.api.proyecto.modules.gender.domain.model.Gender;
import com.fairies.api.proyecto.modules.gender.infrastructure.rest.mapper.GenderMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {FormatMapper.class, AuthorMapper.class, GenderMapper.class})
public interface BookMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "format", source = "formatId", qualifiedByName = "mapFormat")
    @Mapping(target = "authors", source = "authorIds", qualifiedByName = "mapAuthors")
    @Mapping(target = "genres", source = "genreIds", qualifiedByName = "mapGenres")
    Book toDomain(BookRequest request);

    BookResponse toResponse(Book book);

    @Named("mapFormat")
    default Format mapFormat(Long id) {
        if (id == null) return null;
        Format format = new Format();
        format.setId(id);
        return format;
    }

    @Named("mapAuthors")
    default Set<Author> mapAuthors(Set<Long> ids) {
        if (ids == null) return null;
        return ids.stream().map(id -> {
            Author author = new Author();
            author.setId(id);
            return author;
        }).collect(Collectors.toSet());
    }

    @Named("mapGenres")
    default Set<Gender> mapGenres(Set<Long> ids) {
        if (ids == null) return null;
        return ids.stream().map(id -> {
            Gender gender = new Gender();
            gender.setId(id);
            return gender;
        }).collect(Collectors.toSet());
    }
}