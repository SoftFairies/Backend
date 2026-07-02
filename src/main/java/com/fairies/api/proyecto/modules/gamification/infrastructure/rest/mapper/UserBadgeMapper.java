package com.fairies.api.proyecto.modules.gamification.infrastructure.rest.mapper;

import com.fairies.api.proyecto.modules.gamification.domain.model.UserBadge;
import com.fairies.api.proyecto.modules.gamification.infrastructure.rest.dto.UserBadgeResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserBadgeMapper {

    @Mapping(target = "badgeId", source = "badge.id")
    @Mapping(target = "name", source = "badge.name")
    @Mapping(target = "description", source = "badge.description")
    @Mapping(target = "url", source = "badge.url")
    @Mapping(target = "earnedAt", source = "createdAt")
    UserBadgeResponse toResponse(UserBadge domain);
}