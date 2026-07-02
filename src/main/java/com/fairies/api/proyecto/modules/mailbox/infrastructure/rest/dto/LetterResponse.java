package com.fairies.api.proyecto.modules.mailbox.infrastructure.rest.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record LetterResponse(
         Long id,
         UUID bookId,
         UUID senderId,
         String content,
         LocalDateTime sentAt,
         LocalDateTime unlockAt
) { }

