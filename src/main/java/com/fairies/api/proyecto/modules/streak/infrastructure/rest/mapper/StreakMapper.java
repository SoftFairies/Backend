package com.fairies.api.proyecto.modules.streak.infrastructure.rest.mapper;

import com.fairies.api.proyecto.modules.streak.domain.model.UserStreak;
import com.fairies.api.proyecto.modules.streak.infrastructure.rest.dto.UserStreakResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StreakMapper {

    UserStreakResponse toResponse(UserStreak userStreak);
}