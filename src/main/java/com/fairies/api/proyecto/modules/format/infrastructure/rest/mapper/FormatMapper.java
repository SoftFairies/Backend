package com.fairies.api.proyecto.modules.format.infrastructure.rest.mapper;

import com.fairies.api.proyecto.common.infrastructure.rest.dto.CatalogPlainRequest;
import com.fairies.api.proyecto.common.infrastructure.rest.dto.UpdateCatalogPlainRequest;
import com.fairies.api.proyecto.modules.format.domain.model.Format;
import com.fairies.api.proyecto.modules.format.infrastructure.rest.dto.FormatResponse;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface FormatMapper {
    FormatResponse toResponse(Format domain);

    @Mapping(target = "id", ignore = true)
    Format toDomain(CatalogPlainRequest request);

    @Mapping(target = "id", ignore = true)
    void updateFromRequest(UpdateCatalogPlainRequest source, @MappingTarget Format target);
}
