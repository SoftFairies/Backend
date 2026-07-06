package com.fairies.api.proyecto.modules.mailbox.infrastructure.rest.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record LetterResponse(
         Long id,
         String bookName,
         String content,
         LocalDateTime unlockAt
) { }

