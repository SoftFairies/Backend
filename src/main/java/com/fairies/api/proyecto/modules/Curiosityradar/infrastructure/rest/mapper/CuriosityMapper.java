package com.fairies.api.proyecto.modules.Curiosityradar.infrastructure.rest.mapper;

import com.fairies.api.proyecto.modules.Curiosityradar.domain.model.Curiosity;
import com.fairies.api.proyecto.modules.Curiosityradar.infrastructure.rest.dto.CuriosityResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CuriosityMapper {

    CuriosityResponse toResponse(Curiosity domain);
}

