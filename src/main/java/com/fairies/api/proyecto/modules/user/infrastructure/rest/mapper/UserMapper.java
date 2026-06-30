package com.fairies.api.proyecto.modules.user.infrastructure.rest.mapper;

import com.fairies.api.proyecto.modules.auth.infrastructure.rest.dto.RegisterRequest;
import com.fairies.api.proyecto.modules.user.domain.model.User;
import com.fairies.api.proyecto.modules.user.infrastructure.rest.dto.UpdateUserRequest;
import com.fairies.api.proyecto.modules.user.infrastructure.rest.dto.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface UserMapper {

    @Mapping(source = "role.name", target = "roleName")
    @Mapping(source = "profilePicture.id", target = "pictureId")
    @Mapping(source = "profilePicture.url", target = "pictureUrl")
    UserResponse toResponse(User domain);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "roleId", target = "role.id")
    @Mapping(target = "profilePicture", ignore = true)
    @Mapping(target = "deleted", constant = "false")
    @Mapping(target = "deletedAt", ignore = true)
    User toDomain(RegisterRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "profilePicture", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(source = "pictureId", target = "profilePicture.id")
    void updateFromRequest(UpdateUserRequest request, @MappingTarget User target);
}