package com.fairies.api.proyecto.modules.format.application;

import com.fairies.api.proyecto.common.infrastructure.rest.exception.ResourceNotFoundException;
import com.fairies.api.proyecto.modules.format.domain.model.Format;
import com.fairies.api.proyecto.modules.format.infrastructure.persistence.FormatRepository;
import com.fairies.api.proyecto.modules.format.infrastructure.rest.dto.FormatResponse;
import com.fairies.api.proyecto.modules.format.infrastructure.rest.mapper.FormatMapper;
import org.springframework.stereotype.Component;


@Component
public class GetByIdFormatUseCase {
    private final FormatRepository repository;
    private final FormatMapper mapper;

    public GetByIdFormatUseCase(FormatRepository repository, FormatMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public FormatResponse execute(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("El ID no puede ser nulo.");
        }
        Format entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Format not found with ID: " + id));
        return mapper.toResponse(entity);
    }
}
