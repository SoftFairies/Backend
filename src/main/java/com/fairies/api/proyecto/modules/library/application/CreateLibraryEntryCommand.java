package com.fairies.api.proyecto.modules.library.application;

import java.util.UUID;

public record CreateLibraryEntryCommand(
        UUID userId,
        UUID bookId,
        Long readingStatusId,
        int currentPage
) {}