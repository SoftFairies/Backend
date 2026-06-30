package com.fairies.api.proyecto.modules.badge.infrastructure.rest.mapper;

import com.fairies.api.proyecto.common.infrastructure.rest.dto.CatalogMultipartRequest;
import com.fairies.api.proyecto.common.infrastructure.rest.dto.UpdateCatalogMultipartRequest;
import com.fairies.api.proyecto.modules.badge.domain.model.Badge;
import com.fairies.api.proyecto.modules.badge.infrastructure.rest.dto.BadgeResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BadgeMapper {

    BadgeResponse toResponse(Badge domain);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "url", ignore = true)
    @Mapping(target = "publicId", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    Badge toDomain(CatalogMultipartRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "url", ignore = true)
    @Mapping(target = "publicId", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    Badge toDomain(UpdateCatalogMultipartRequest request);
}