package com.fairies.api.proyecto.modules.readingStatus.infrastructure.rest.mapper;

import com.fairies.api.proyecto.common.infrastructure.rest.dto.CatalogPlainRequest;
import com.fairies.api.proyecto.common.infrastructure.rest.dto.UpdateCatalogPlainRequest;
import com.fairies.api.proyecto.modules.readingStatus.domain.model.ReadingStatus;
import com.fairies.api.proyecto.modules.readingStatus.infrastructure.rest.dto.ReadingStatusResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReadingStatusMapper {

    ReadingStatusResponse toResponse(ReadingStatus domain);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    ReadingStatus toDomain(CatalogPlainRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    ReadingStatus toDomain(UpdateCatalogPlainRequest request);
}
