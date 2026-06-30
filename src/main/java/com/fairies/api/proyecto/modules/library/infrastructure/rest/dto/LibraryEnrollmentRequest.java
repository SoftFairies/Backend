package com.fairies.api.proyecto.modules.library.infrastructure.rest.dto;

import com.fairies.api.proyecto.modules.book.infrastructure.rest.dto.BookRequest;

public record LibraryEnrollmentRequest(
        String externalId,
        BookRequest bookData,
        String readingStatusId,
        int currentPage
) {}