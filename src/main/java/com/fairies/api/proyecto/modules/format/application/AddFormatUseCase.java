package com.fairies.api.proyecto.modules.format.application;

import com.fairies.api.proyecto.common.infrastructure.rest.dto.CatalogPlainRequest;
import com.fairies.api.proyecto.modules.format.domain.model.Format;
import com.fairies.api.proyecto.modules.format.infrastructure.persistence.FormatRepository;
import com.fairies.api.proyecto.modules.format.infrastructure.rest.dto.FormatResponse;
import com.fairies.api.proyecto.modules.format.infrastructure.rest.mapper.FormatMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class AddFormatUseCase {
    private final FormatRepository repository;
    private final FormatMapper mapper;

    @Transactional
    public FormatResponse execute(CatalogPlainRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("La petición no puede ser nula.");
        }

        Format entity = mapper.toDomain(request);

        return mapper.toResponse(repository.save(entity));
    }
}
