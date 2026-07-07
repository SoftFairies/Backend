package com.fairies.api.proyecto.modules.format.application;

import com.fairies.api.proyecto.common.infrastructure.rest.dto.UpdateCatalogPlainRequest;
import com.fairies.api.proyecto.common.infrastructure.rest.exception.ResourceNotFoundException;
import com.fairies.api.proyecto.modules.format.domain.model.Format;
import com.fairies.api.proyecto.modules.format.infrastructure.persistence.FormatRepository;
import com.fairies.api.proyecto.modules.format.infrastructure.rest.dto.FormatResponse;
import com.fairies.api.proyecto.modules.format.infrastructure.rest.mapper.FormatMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class UpdateFormatUseCase {
    private final FormatRepository repository;
    private final FormatMapper mapper;

    @Transactional
    public FormatResponse execute(Long id, UpdateCatalogPlainRequest request) {
        if (id == null) {
            throw new IllegalArgumentException("El ID no puede ser nulo.");
        }

        Format existingEntity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Format not found with ID: " + id));

        mapper.updateFromRequest(request, existingEntity);

        return mapper.toResponse(repository.save(existingEntity));
    }
}
