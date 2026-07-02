package com.fairies.api.proyecto.modules.format.application;

import com.fairies.api.proyecto.modules.format.infrastructure.persistence.FormatRepository;
import com.fairies.api.proyecto.modules.format.infrastructure.rest.dto.FormatResponse;
import com.fairies.api.proyecto.modules.format.infrastructure.rest.mapper.FormatMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class GetAllFormatUseCase {
    private final FormatRepository repository;
    private final FormatMapper mapper;

    public GetAllFormatUseCase(FormatRepository repository, FormatMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public Page<FormatResponse> execute(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toResponse);
    }
}
