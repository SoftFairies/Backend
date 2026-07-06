package com.fairies.api.proyecto.modules.mailbox.infrastructure.rest.mapper;

import com.fairies.api.proyecto.common.infrastructure.rest.GlobalMapperConfig;
import com.fairies.api.proyecto.modules.mailbox.domain.model.Letter;
import com.fairies.api.proyecto.modules.mailbox.infrastructure.rest.dto.LetterResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(config = GlobalMapperConfig.class)
public interface LetterMapper {

    @Mapping(source = "recommendationContent.book.title", target = "bookName")
    @Mapping(source = "recommendationContent.content", target = "content")
    LetterResponse toResponse(Letter letter);

    List<LetterResponse> toResponseList(List<Letter> letters);
}