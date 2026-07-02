package com.fairies.api.proyecto.modules.library.infrastructure.rest.dto;

import com.fairies.api.proyecto.modules.book.infrastructure.rest.dto.BookRequest;

import java.util.UUID;

public record LibraryEnrollmentRequest(
        UUID bookId,
        BookRequest bookData,
        Long readingStatusId,
        int currentPage
) {}