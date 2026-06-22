package com.fairies.api.proyecto.modules.badge.infrastructure.rest;

import com.fairies.api.proyecto.common.infrastructure.rest.dto.CatalogMultipartRequest;
import com.fairies.api.proyecto.common.infrastructure.rest.dto.UpdateCatalogMultipartRequest;
import com.fairies.api.proyecto.modules.badge.application.*;
import com.fairies.api.proyecto.modules.badge.domain.model.Badge;
import com.fairies.api.proyecto.modules.badge.infrastructure.rest.dto.BadgeResponse;
import com.fairies.api.proyecto.modules.badge.infrastructure.rest.mapper.BadgeMapper;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/badges")
public class BadgeRouting {

    private final AddBadgeUseCase addUseCase;
    private final GetAllBadgeUseCase getAllUseCase;
    private final GetByIdBadgeUseCase getByIdUseCase;
    private final UpdateBadgeUseCase updateUseCase;
    private final DeleteBadgeUseCase deleteUseCase;
    private final BadgeMapper mapper;

    public BadgeRouting(
            AddBadgeUseCase addUseCase,
            GetAllBadgeUseCase getAllUseCase,
            GetByIdBadgeUseCase getByIdUseCase,
            UpdateBadgeUseCase updateUseCase,
            DeleteBadgeUseCase deleteUseCase,
            BadgeMapper mapper
    ) {
        this.addUseCase = addUseCase;
        this.getAllUseCase = getAllUseCase;
        this.getByIdUseCase = getByIdUseCase;
        this.updateUseCase = updateUseCase;
        this.deleteUseCase = deleteUseCase;
        this.mapper = mapper;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Creates a new badge")
    public BadgeResponse create(@Valid @ModelAttribute CatalogMultipartRequest request) {
        Badge badgeDomain = mapper.toDomain(request);
        Badge savedBadge = addUseCase.execute(request.file(), badgeDomain);
        return mapper.toResponse(savedBadge);
    }

    @GetMapping
    @Operation(summary = "Get all badges with pagination")
    public Page<BadgeResponse> getAll(@PageableDefault(size = 10, page = 0) Pageable pageable) {
        return getAllUseCase.execute(pageable).map(mapper::toResponse);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get badge by ID")
    public BadgeResponse getById(@PathVariable Long id) {
        Badge badge = getByIdUseCase.execute(id);
        return mapper.toResponse(badge);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Updates an existing badge")
    public BadgeResponse update(@PathVariable Long id, @Valid @ModelAttribute UpdateCatalogMultipartRequest request) {
        Badge updatedFields = mapper.toDomain(request);
        Badge updatedBadge = updateUseCase.execute(id, request.file(), updatedFields);
        return mapper.toResponse(updatedBadge);
    }

    @DeleteMapping("/{id}")
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete badge by ID")
    public void delete(@PathVariable Long id) {
        deleteUseCase.execute(id);
    }
}