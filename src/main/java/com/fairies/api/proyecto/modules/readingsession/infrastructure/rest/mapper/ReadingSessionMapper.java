package com.fairies.api.proyecto.modules.readingsession.infrastructure.rest.mapper;

import com.fairies.api.proyecto.common.infrastructure.rest.GlobalMapperConfig;
import com.fairies.api.proyecto.modules.readingsession.domain.model.ReadingSession;
import com.fairies.api.proyecto.modules.readingsession.infrastructure.rest.dto.ReadingSessionResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = GlobalMapperConfig.class)
public interface ReadingSessionMapper {

    @Mapping(source = "userLibrary.id", target = "libraryId")
    @Mapping(source = "userLibrary.book.title", target = "bookTitle")
    @Mapping(source = "userLibrary.book.cover", target = "bookCover")
    ReadingSessionResponse toResponse(ReadingSession domain);

}