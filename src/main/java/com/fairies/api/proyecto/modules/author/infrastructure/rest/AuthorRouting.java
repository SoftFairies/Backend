package com.fairies.api.proyecto.modules.author.infrastructure.rest;

import com.fairies.api.proyecto.common.infrastructure.rest.dto.CatalogPlainRequest;
import com.fairies.api.proyecto.common.infrastructure.rest.dto.UpdateCatalogPlainRequest;
import com.fairies.api.proyecto.modules.author.application.*;
import com.fairies.api.proyecto.modules.author.domain.model.Author;
import com.fairies.api.proyecto.modules.author.infrastructure.rest.dto.AuthorResponse;
import com.fairies.api.proyecto.modules.author.infrastructure.rest.mapper.AuthorMapper;
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
@RequiredArgsConstructor
@RequestMapping("api/v1/authors")
public class AuthorRouting {

    private final AddAuthorUseCase addUseCase;
    private final GetAllAuthorUseCase getAllUseCase;
    private final GetByIdAuthorUseCase getByIdUseCase;
    private final UpdateAuthorUseCase updateUseCase;
    private final DeleteAuthorUseCase deleteUseCase;
    private final AuthorMapper mapper;

    @PostMapping
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Creates a new author")
    public AuthorResponse create(@Valid @RequestBody CatalogPlainRequest request) {
        Author domain = mapper.toDomain(request);
        Author saved = addUseCase.execute(domain);
        return mapper.toResponse(saved);
    }

    @GetMapping
    @Operation(summary = "Get all authors with pagination and optional search")
    public Page<AuthorResponse> getAll(
            @RequestParam(required = false) String query,
            @PageableDefault(size = 10, page = 0) Pageable pageable) {
        return getAllUseCase.execute(query, pageable).map(mapper::toResponse);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get author by ID")
    public AuthorResponse getById(@PathVariable Long id) {
        Author entity = getByIdUseCase.execute(id);
        return mapper.toResponse(entity);
    }

    @PutMapping("/{id}")
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Updates an existing author")
    public AuthorResponse update(@PathVariable Long id, @Valid @RequestBody UpdateCatalogPlainRequest request) {
        Author updatedFields = mapper.toDomain(request);
        Author updated = updateUseCase.execute(id, updatedFields);
        return mapper.toResponse(updated);
    }

    @DeleteMapping("/{id}")
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete author by ID")
    public void delete(@PathVariable Long id) {
        deleteUseCase.execute(id);
    }
}
