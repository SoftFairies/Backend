package com.fairies.api.proyecto.modules.library.infrastructure.rest;

import com.fairies.api.proyecto.common.application.security.JwtService;
import com.fairies.api.proyecto.common.infrastructure.rest.exception.ResourceNotFoundException;
import com.fairies.api.proyecto.modules.library.application.*;
import com.fairies.api.proyecto.modules.library.domain.model.LibraryNote;
import com.fairies.api.proyecto.modules.library.domain.model.UserLibrary;
import com.fairies.api.proyecto.modules.library.infrastructure.rest.dto.*;
import com.fairies.api.proyecto.modules.library.infrastructure.rest.mapper.LibraryMapper;
import com.fairies.api.proyecto.modules.user.application.GetByIdUserUseCase;
import com.fairies.api.proyecto.modules.user.domain.model.User;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/library")
@RequiredArgsConstructor
public class LibraryRouting {

    private final AddLibraryUseCase addUseCase;
    private final GetAllLibraryUseCase getAllUseCase;
    private final GetByIdLibraryUseCase getByIdLibraryUseCase;
    private final UpdateLibraryUseCase updateUseCase;
    private final DeleteLibraryUseCase deleteUseCase;
    private final GetByIdUserUseCase getByIdUserUseCase;
    private final AddNoteUseCase addNoteUseCase;
    private final GetNotesByLibraryUseCase getNotesUseCase;
    private final JwtService jwtService;
    private final LibraryMapper mapper;

    @PostMapping
    @Operation(summary = "Agrega un libro (existente o nuevo) a la biblioteca personal del usuario autenticado")
    public ResponseEntity<Void> add(
            @Valid @RequestBody AddLibraryEntryRequest request,
            @RequestHeader("Authorization") String authHeader
    ) {
        UUID userId = jwtService.getUserIdFromToken(authHeader);
        User user = getByIdUserUseCase.execute(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        addUseCase.execute(user, request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    @Operation(summary = "Obtiene todos los libros en la biblioteca personal del usuario autenticado con paginación")
    public ResponseEntity<Page<LibraryEntryResponse>> getAll(
            @RequestHeader("Authorization") String authHeader,
            Pageable pageable
    ) {
        UUID userId = jwtService.getUserIdFromToken(authHeader);
        return ResponseEntity.ok(getAllUseCase.execute(userId, pageable).map(mapper::toResponse));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtiene los detalles de un registro específico de la biblioteca personal por ID")
    public ResponseEntity<LibraryEntryResponse> getById(
            @PathVariable UUID id,
            @RequestHeader("Authorization") String authHeader
    ) {
        UUID userId = jwtService.getUserIdFromToken(authHeader);
        UserLibrary library = getByIdLibraryUseCase.execute(id, userId);
        return ResponseEntity.ok(mapper.toResponse(library));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Actualiza parcialmente el progreso, estado o formato de un libro en la biblioteca personal")
    public ResponseEntity<LibraryEntryResponse> update(
            @PathVariable UUID id,
            @RequestBody UpdateLibraryEntryRequest request,
            @RequestHeader("Authorization") String authHeader
    ) {
        UUID userId = jwtService.getUserIdFromToken(authHeader);
        UserLibrary updatedLibrary = updateUseCase.execute(userId, id, request);
        return ResponseEntity.ok(mapper.toResponse(updatedLibrary));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina un libro de la biblioteca personal del usuario autenticado")
    public ResponseEntity<Void> delete(
            @PathVariable UUID id,
            @RequestHeader("Authorization") String authHeader
    ) {
        UUID userId = jwtService.getUserIdFromToken(authHeader);
        deleteUseCase.execute(userId, id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{libraryId}/notes")
    @Operation(summary = "Agrega una nota o cita de lectura vinculada a un registro de biblioteca específico")
    public ResponseEntity<LibraryNoteResponse> addNote(
            @PathVariable UUID libraryId,
            @Valid @RequestBody LibraryNoteRequest request
    ) {
        LibraryNote savedNote = addNoteUseCase.execute(libraryId, request);
        LibraryNoteResponse responseDto = mapper.toNoteResponse(savedNote);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping("/{libraryId}/notes")
    @Operation(summary = "Obtiene todas las notas guardadas de un libro en la biblioteca, ordenadas por capítulo y página")
    public ResponseEntity<List<LibraryNoteResponse>> getNotes(
            @PathVariable UUID libraryId,
            @RequestHeader("Authorization") String authHeader
    ) {
        UUID userId = jwtService.getUserIdFromToken(authHeader);
        var notes = getNotesUseCase.execute(libraryId);
        var response = notes.stream()
                .map(mapper::toNoteResponse)
                .toList();
        return ResponseEntity.ok(response);
    }
}