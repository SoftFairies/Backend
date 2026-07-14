package com.fairies.api.proyecto.modules.readingStatus.infrastructure.rest;

import com.fairies.api.proyecto.common.infrastructure.rest.dto.CatalogPlainRequest;
import com.fairies.api.proyecto.common.infrastructure.rest.dto.UpdateCatalogPlainRequest;
import com.fairies.api.proyecto.modules.readingStatus.application.*;
import com.fairies.api.proyecto.modules.readingStatus.domain.model.ReadingStatus;
import com.fairies.api.proyecto.modules.readingStatus.infrastructure.rest.dto.ReadingStatusResponse;
import com.fairies.api.proyecto.modules.readingStatus.infrastructure.rest.mapper.ReadingStatusMapper;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;


@RestController
@RequestMapping("api/v1/reading-status")
@RequiredArgsConstructor
public class ReadingStatusRouting {

    private final AddReadingStatusUseCase addUseCase;
    private final GetAllReadingStatusUseCase getAllUseCase;
    private final GetByIdReadingStatusUseCase getByIdUseCase;
    private final UpdateReadingStatusUseCase updateUseCase;
    private final DeleteReadingStatusUseCase deleteUseCase;
    private final ReadingStatusMapper mapper;

    @PostMapping
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Creates a new readingStatus")
    public ReadingStatusResponse create(@Valid @RequestBody CatalogPlainRequest request) {
        ReadingStatus domain = mapper.toDomain(request);
        ReadingStatus saved = addUseCase.execute(domain);
        return mapper.toResponse(saved);
    }

    @GetMapping
    @Operation(summary = "Get all readingStatuss with pagination")
    public Page<ReadingStatusResponse> getAll(@PageableDefault(size = 10, page = 0) Pageable pageable) {
        return getAllUseCase.execute(pageable).map(mapper::toResponse);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get readingStatus by ID")
    public ReadingStatusResponse getById(@PathVariable Long id) {
        ReadingStatus entity = getByIdUseCase.execute(id);
        return mapper.toResponse(entity);
    }

    @PutMapping("/{id}")
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Updates an existing readingStatus")
    public ReadingStatusResponse update(@PathVariable Long id, @Valid @RequestBody UpdateCatalogPlainRequest request) {
        ReadingStatus updatedFields = mapper.toDomain(request);
        ReadingStatus updated = updateUseCase.execute(id, updatedFields);
        return mapper.toResponse(updated);
    }

    @DeleteMapping("/{id}")
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete readingStatus by ID")
    public void delete(@PathVariable Long id) {
        deleteUseCase.execute(id);
    }
}
