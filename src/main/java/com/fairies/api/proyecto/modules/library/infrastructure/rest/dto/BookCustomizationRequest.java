package com.fairies.api.proyecto.modules.library.infrastructure.rest.dto;

public record BookCustomizationRequest(
        String customTitle,
        Integer customChapters,
        Integer customPages,
        String customCoverType,
        String customCoverValue
) {}