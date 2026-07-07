package com.fairies.api.proyecto.modules.format.application;

import com.fairies.api.proyecto.modules.format.infrastructure.persistence.FormatRepository;
import com.fairies.api.proyecto.modules.format.infrastructure.rest.dto.FormatResponse;
import com.fairies.api.proyecto.modules.format.infrastructure.rest.mapper.FormatMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetAllFormatUseCase {
    private final FormatRepository repository;
    private final FormatMapper mapper;

    @Transactional
    public Page<FormatResponse> execute(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toResponse);
    }
}
