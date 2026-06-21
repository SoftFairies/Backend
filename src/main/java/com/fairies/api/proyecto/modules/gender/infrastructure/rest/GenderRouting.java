package com.fairies.api.proyecto.modules.gender.infrastructure.rest;

import com.fairies.api.proyecto.common.infrastructure.rest.dto.CatalogPlainRequest;
import com.fairies.api.proyecto.common.infrastructure.rest.dto.UpdateCatalogPlainRequest;
import com.fairies.api.proyecto.modules.gender.application.*;
import com.fairies.api.proyecto.modules.gender.domain.model.Gender;
import com.fairies.api.proyecto.modules.gender.infrastructure.rest.dto.GenderResponse;
import com.fairies.api.proyecto.modules.gender.infrastructure.rest.mapper.GenderMapper;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/genders")
public class GenderRouting {

    private final AddGenderUseCase addUseCase;
    private final GetAllGenderUseCase getAllUseCase;
    private final GetByIdGenderUseCase getByIdUseCase;
    private final UpdateGenderUseCase updateUseCase;
    private final DeleteGenderUseCase deleteUseCase;
    private final GenderMapper mapper;

    public GenderRouting(
            AddGenderUseCase addUseCase,
            GetAllGenderUseCase getAllUseCase,
            GetByIdGenderUseCase getByIdUseCase,
            UpdateGenderUseCase updateUseCase,
            DeleteGenderUseCase deleteUseCase,
            GenderMapper mapper
    ) {
        this.addUseCase = addUseCase;
        this.getAllUseCase = getAllUseCase;
        this.getByIdUseCase = getByIdUseCase;
        this.updateUseCase = updateUseCase;
        this.deleteUseCase = deleteUseCase;
        this.mapper = mapper;
    }

    @PostMapping
    @Transactional
    //@PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Creates a new gender")
    public GenderResponse create(@Valid @RequestBody CatalogPlainRequest request) {
        Gender genderDomain = mapper.toDomain(request);
        Gender savedGender = addUseCase.execute(genderDomain);
        return mapper.toResponse(savedGender);
    }

    @GetMapping
    @Operation(summary = "Get all genders with pagination")
    public Page<GenderResponse> getAll(@PageableDefault(size = 10, page = 0) Pageable pageable) {
        return getAllUseCase.execute(pageable).map(mapper::toResponse);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get gender by ID")
    public GenderResponse getById(@PathVariable Long id) {
        Gender gender = getByIdUseCase.execute(id);
        return mapper.toResponse(gender);
    }

    @PutMapping("/{id}")
    @Transactional
    //@PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Updates an existing gender")
    public GenderResponse update(@PathVariable Long id, @Valid @RequestBody UpdateCatalogPlainRequest request) {
        Gender updatedFields = mapper.toDomain(request);
        Gender updatedGender = updateUseCase.execute(id, updatedFields);
        return mapper.toResponse(updatedGender);
    }

    @DeleteMapping("/{id}")
    @Transactional
    //@PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete gender by ID")
    public void delete(@PathVariable Long id) {
        deleteUseCase.execute(id);
    }
}