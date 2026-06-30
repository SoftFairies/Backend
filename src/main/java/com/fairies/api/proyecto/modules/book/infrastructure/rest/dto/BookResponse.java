package com.fairies.api.proyecto.modules.book.infrastructure.rest.dto;

import com.fairies.api.proyecto.modules.author.infrastructure.rest.dto.AuthorResponse;
import com.fairies.api.proyecto.modules.format.infrastructure.rest.dto.FormatResponse;
import com.fairies.api.proyecto.modules.gender.infrastructure.rest.dto.GenderResponse;

import java.util.Set;
import java.util.UUID;

public record BookResponse(
        UUID id,
        String isbn,
        String title,
        Integer defaultChapters,
        Integer defaultPages,
        String origin,
        String coverType,
        String coverValue,
        FormatResponse format,
        Set<AuthorResponse> authors,
        Set<GenderResponse> genres
) {}