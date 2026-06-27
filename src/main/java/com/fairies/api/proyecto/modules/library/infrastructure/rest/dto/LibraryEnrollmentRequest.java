package com.fairies.api.proyecto.modules.library.infrastructure.rest.dto;

import java.util.UUID;

public record LibraryEnrollmentRequest(
        UUID bookId,
        BookExternalSyncRequest externalBook,
        LibraryProgressRequest progress
) {}