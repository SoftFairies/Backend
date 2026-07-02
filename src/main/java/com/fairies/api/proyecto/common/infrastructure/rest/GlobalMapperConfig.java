package com.fairies.api.proyecto.common.infrastructure.rest;

import org.mapstruct.MapperConfig;
import org.mapstruct.ReportingPolicy;

@MapperConfig(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE // Ignora campos no mapeados globalmente en este módulo para no repetir @Mapping(ignore=true)
)

public interface GlobalMapperConfig {
}
