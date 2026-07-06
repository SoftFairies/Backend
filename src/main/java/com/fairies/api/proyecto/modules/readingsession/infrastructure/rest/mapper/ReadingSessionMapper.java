package com.fairies.api.proyecto.modules.readingsession.infrastructure.rest.mapper;

import com.fairies.api.proyecto.modules.readingsession.domain.model.ReadingSession;
import com.fairies.api.proyecto.modules.readingsession.infrastructure.rest.dto.ReadingSessionRequest;
import com.fairies.api.proyecto.modules.readingsession.infrastructure.rest.dto.ReadingSessionResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.UUID;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReadingSessionMapper {

    @Mapping(target = "book.id", source = "libroId")
    @Mapping(target = "user.id", source = "usuarioId")
    ReadingSession toDomain(ReadingSessionRequest request);

    ReadingSessionResponse toResponse(ReadingSession domain);

    @org.mapstruct.Named("stringToUuid")
    default UUID stringToUuid(String id) {
        if (id == null || id.isBlank()) return null;
        return UUID.fromString(id);
    }
}