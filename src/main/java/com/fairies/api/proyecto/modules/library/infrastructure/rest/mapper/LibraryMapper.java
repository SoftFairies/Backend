package com.fairies.api.proyecto.modules.library.infrastructure.rest.mapper;

import com.fairies.api.proyecto.common.infrastructure.rest.GlobalMapperConfig;
import com.fairies.api.proyecto.modules.book.infrastructure.rest.mapper.BookMapper;
import com.fairies.api.proyecto.modules.library.domain.model.LibraryNote;
import com.fairies.api.proyecto.modules.library.domain.model.UserLibrary;
import com.fairies.api.proyecto.modules.library.infrastructure.rest.dto.LibraryEntryResponse;
import com.fairies.api.proyecto.modules.library.infrastructure.rest.dto.LibraryNoteResponse;
import com.fairies.api.proyecto.modules.library.infrastructure.rest.dto.UpdateLibraryEntryRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = GlobalMapperConfig.class, uses = {BookMapper.class})
public interface LibraryMapper {

    @Mapping(source = "readingStatus.name", target = "readingStatusName")
    @Mapping(source = "format.name", target = "formatName")
        // @Mapping(source = "customTitle", target = "book.title", conditionExpression = "java(userLibrary.getCustomTitle() != null)")
    LibraryEntryResponse toResponse(UserLibrary userLibrary);

    @Mapping(source = "userLibrary.book.title", target = "bookTitle")
    LibraryNoteResponse toNoteResponse(LibraryNote note);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "book", ignore = true)
    @Mapping(target = "readingStatus", ignore = true)
    @Mapping(target = "format", ignore = true)
    void updateFromRequest(UpdateLibraryEntryRequest request, @MappingTarget UserLibrary target);
}