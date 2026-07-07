package com.fairies.api.proyecto.modules.picture.infrastructure.rest;

import com.fairies.api.proyecto.common.infrastructure.rest.dto.UpdateCatalogMultipartRequest;
import com.fairies.api.proyecto.modules.picture.application.*;
import com.fairies.api.proyecto.modules.picture.domain.model.Picture;
import com.fairies.api.proyecto.modules.picture.infrastructure.rest.dto.PictureResponse;
import com.fairies.api.proyecto.modules.picture.infrastructure.rest.mapper.PictureMapper;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/pictures")
public class PictureRouting {

    private final AddPictureUseCase addPictureUseCase;
    private final GetAllPicturesUseCase getAllUseCase;
    private final GetPictureByIdUseCase getByIdUseCase;
    private final UpdatePictureUseCase updateUseCase;
    private final DeletePictureUseCase deleteUseCase;
    private final PictureMapper mapper;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Creates a new picture")
    public PictureResponse create(@Valid @ModelAttribute UpdateCatalogMultipartRequest request) {
        Picture pictureDomain = mapper.toDomain(request);
        Picture savedPicture = addPictureUseCase.execute(request.file(), pictureDomain);
        return mapper.toResponse(savedPicture);
    }

    @GetMapping
    @Operation(summary = "Get all pictures with pagination")
    public Page<PictureResponse> getAll(@PageableDefault(size = 10, page = 0) Pageable pageable) {
        return getAllUseCase.execute(pageable).map(mapper::toResponse);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get picture by ID")
    public PictureResponse getById(@PathVariable Long id) {
        Picture picture = getByIdUseCase.execute(id);
        return mapper.toResponse(picture);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Updates an existing picture")
    public PictureResponse update(@PathVariable Long id, @Valid @ModelAttribute UpdateCatalogMultipartRequest request) {
        Picture updatedFields = mapper.toDomain(request);
        Picture updatedPicture = updateUseCase.execute(id, request.file(), updatedFields);
        return mapper.toResponse(updatedPicture);
    }

    @DeleteMapping("/{id}")
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete picture by ID")
    public void delete(@PathVariable Long id) {
        deleteUseCase.execute(id);
    }
}