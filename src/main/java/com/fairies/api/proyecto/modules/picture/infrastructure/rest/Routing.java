package com.fairies.api.proyecto.modules.picture.infrastructure.rest;

import com.fairies.api.proyecto.modules.picture.application.*;
import com.fairies.api.proyecto.modules.picture.infrastructure.rest.dto.*;
import com.fairies.api.proyecto.modules.picture.infrastructure.rest.mapper.PictureMapper;
import io.swagger.v3.oas.annotations.Operation;
import org.hibernate.engine.transaction.jta.platform.internal.SynchronizationRegistryBasedSynchronizationStrategy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;


@RestController
@RequestMapping("api/v1/pictures")
public class Routing {

    private final AddPictureUseCase addPictureUseCase;
    private final GetAllPicturesUseCase getAllUseCase;
    private final GetPictureByIdUseCase getByIdUseCase;
    private final PictureMapper mapper;

    public Routing(
            AddPictureUseCase addUseCase,
            GetAllPicturesUseCase getAllUseCase,
            GetPictureByIdUseCase getByIdUseCase,
            PictureMapper mapper
    ) {
        this.addPictureUseCase = addUseCase;
        this.getAllUseCase = getAllUseCase;
        this.getByIdUseCase = getByIdUseCase;
        this.mapper = mapper;
    }


    @Transactional
    //@PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Creates a new picture")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public PictureResponse create(@Valid @ModelAttribute PictureRequests request) {
        return mapper.toResponse(addPictureUseCase.execute(request.file(), request.name()));
    }


    @GetMapping
    //@PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get all pictures with pagination")
    public Page<PictureResponse> getAll(@PageableDefault(size = 10, page = 0) Pageable pageable) {
        return getAllUseCase.execute(pageable).map(mapper::toResponse);
    }

    @GetMapping("/{id}")
    //@PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get picture by ID")
    public PictureResponse getById(@PathVariable Long id) {
        return mapper.toResponse(getByIdUseCase.execute(id));
    }

    /*
    @PutMapping("/{id}")
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Updates an existing picture")
    public Response update(@PathVariable Long id, @Valid @RequestBody Requests request) {

    }

    @DeleteMapping("/{id}")
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete picture by ID")
    public void delete(@PathVariable Long id) {
    }

     */
}
