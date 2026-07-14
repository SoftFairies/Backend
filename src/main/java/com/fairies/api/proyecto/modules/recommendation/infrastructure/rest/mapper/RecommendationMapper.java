package com.fairies.api.proyecto.modules.recommendation.infrastructure.rest.mapper;

import com.fairies.api.proyecto.modules.recommendation.domain.model.UserPreference;
import com.fairies.api.proyecto.modules.recommendation.infrastructure.rest.dto.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface RecommendationMapper {

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "formats", source = "formats")
    @Mapping(target = "genres", source = "genres")
    RecommendationResponse toResponse(UserPreference domain);

    default Set<PreferenceItem> mapFormats(Set<com.fairies.api.proyecto.modules.format.domain.model.Format> formats) {
        return formats.stream().map(f -> new PreferenceItem(f.getId(), f.getName())).collect(Collectors.toSet());
    }

    default Set<PreferenceItem> mapGenres(Set<com.fairies.api.proyecto.modules.gender.domain.model.Gender> genres) {
        return genres.stream().map(g -> new PreferenceItem(g.getId(), g.getName())).collect(Collectors.toSet());
    }
}