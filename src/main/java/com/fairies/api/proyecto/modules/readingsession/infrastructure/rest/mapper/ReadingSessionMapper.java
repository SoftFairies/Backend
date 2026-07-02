package com.fairies.api.proyecto.modules.readingsession.infrastructure.rest.mapper;


import com.fairies.api.proyecto.modules.readingsession.domain.model.ReadingSession;
import com.fairies.api.proyecto.modules.readingsession.infrastructure.rest.dto.ReadingSessionResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReadingSessionMapper {

    @Mapping(target = "usuarioId", source = "user.id")
    ReadingSessionResponse toResponse(ReadingSession domain);

    @Mapping(target = "sesionId", ignore = true)
    @Mapping(target = "user.id", source = "usuarioId")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    ReadingSession toDomain(ReadingSessionResponse response);
}