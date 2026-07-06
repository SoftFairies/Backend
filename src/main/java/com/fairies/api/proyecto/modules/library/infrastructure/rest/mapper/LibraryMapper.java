package com.fairies.api.proyecto.modules.library.infrastructure.rest.mapper;

import com.fairies.api.proyecto.common.infrastructure.rest.GlobalMapperConfig;
import com.fairies.api.proyecto.modules.book.infrastructure.rest.mapper.BookMapper;
import com.fairies.api.proyecto.modules.library.domain.model.UserLibrary;
import com.fairies.api.proyecto.modules.library.infrastructure.rest.dto.LibraryEntryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = GlobalMapperConfig.class, uses = {BookMapper.class})
public interface LibraryMapper {

    @Mapping(source = "readingStatus.name", target = "readingStatusName")
    @Mapping(source = "format.name", target = "formatName")
        // @Mapping(source = "customTitle", target = "book.title", conditionExpression = "java(userLibrary.getCustomTitle() != null)")
    LibraryEntryResponse toResponse(UserLibrary userLibrary);
}