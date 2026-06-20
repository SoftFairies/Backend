package com.fairies.api.proyecto.modules.picture.infrastructure.rest.mapper;

import                                                                    com.fairies.api.proyecto.modules.picture.infrastructure.rest.dto.PictureRequests;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.fairies.api.proyecto.modules.picture.domain.model.Picture;
import com.fairies.api.proyecto.modules.picture.infrastructure.rest.dto.PictureResponse;

@Mapper(componentModel = "spring")
public interface PictureMapper {
    PictureResponse toResponse(Picture domain);

    @Mapping(target = "id", ignore = true)
    Picture toDomain(PictureRequests request);
}
