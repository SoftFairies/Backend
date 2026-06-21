package com.fairies.api.proyecto.modules.format.infrastructure.rest;

import com.fairies.api.proyecto.common.infrastructure.rest.dto.CatalogPlainRequest;
import com.fairies.api.proyecto.common.infrastructure.rest.dto.UpdateCatalogPlainRequest;
import com.fairies.api.proyecto.modules.format.application.*;
import com.fairies.api.proyecto.modules.format.infrastructure.rest.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;


@RestController
@RequestMapping("api/v1/formats")
public class FormatRouting {

    private final AddFormatUseCase addUseCase;
    private final GetAllFormatUseCase getAllUseCase;
    private final GetByIdFormatUseCase getByIdUseCase;
    private final UpdateFormatUseCase updateUseCase;
    private final DeleteFormatUseCase deleteUseCase;

    public FormatRouting(
            AddFormatUseCase addUseCase,
            GetAllFormatUseCase getAllUseCase,
            GetByIdFormatUseCase getByIdUseCase,
            UpdateFormatUseCase updateUseCase,
            DeleteFormatUseCase deleteUseCase
    ) {
        this.addUseCase = addUseCase;
        this.getAllUseCase = getAllUseCase;
        this.getByIdUseCase = getByIdUseCase;
        this.updateUseCase = updateUseCase;
        this.deleteUseCase = deleteUseCase;
    }

    @PostMapping
    @Transactional
    //@PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Creates a new format")
    public FormatResponse create(@Valid @RequestBody CatalogPlainRequest request) {
        return addUseCase.execute(request);
    }

    @GetMapping
    @Operation(summary = "Get all formats with pagination")
    public Page<FormatResponse> getAll(@PageableDefault(size = 10, page = 0) Pageable pageable) {
        return getAllUseCase.execute(pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get format by ID")
    public FormatResponse getById(@PathVariable Long id) {
        return getByIdUseCase.execute(id);
    }

    @PutMapping("/{id}")
    @Transactional
    //@PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Updates an existing format")
    public FormatResponse update(@PathVariable Long id, @Valid @RequestBody UpdateCatalogPlainRequest request) {
        return updateUseCase.execute(id, request);
    }

    @DeleteMapping("/{id}")
    @Transactional
    //@PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete format by ID")
    public void delete(@PathVariable Long id) {
        deleteUseCase.execute(id);
    }
}
