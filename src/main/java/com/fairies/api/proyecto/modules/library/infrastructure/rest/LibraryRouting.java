package com.fairies.api.proyecto.modules.library.infrastructure.rest;

import com.fairies.api.proyecto.common.application.security.JwtService;
import com.fairies.api.proyecto.modules.book.application.AddBookUseCase;
import com.fairies.api.proyecto.modules.book.domain.model.Book;
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
    public ResponseEntity<Void> add(
            @Valid @RequestBody AddLibraryEntryRequest request,
            @RequestHeader("Authorization") String authHeader
    ) {
        UUID userId = jwtService.getUserIdFromToken(authHeader);
        User user = getByIdUserUseCase.execute(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        addUseCase.execute(user, request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<Page<LibraryEntryResponse>> getAll(
            @RequestHeader("Authorization") String authHeader,
            Pageable pageable
    ) {
        UUID userId = jwtService.getUserIdFromToken(authHeader);
        return ResponseEntity.ok(getAllUseCase.execute(userId, pageable).map(mapper::toResponse));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LibraryEntryResponse> getById(
            @PathVariable UUID id,
            @RequestHeader("Authorization") String authHeader
    ) {
        UUID userId = jwtService.getUserIdFromToken(authHeader);
        UserLibrary library = getByIdLibraryUseCase.execute(id, userId);
        return ResponseEntity.ok(mapper.toResponse(library));
    }

    @PatchMapping("/{id}")
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
    public ResponseEntity<Void> delete(
            @PathVariable UUID id,
            @RequestHeader("Authorization") String authHeader
    ) {
        UUID userId = jwtService.getUserIdFromToken(authHeader);
        deleteUseCase.execute(userId, id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{libraryId}/notes")
    public ResponseEntity<LibraryNoteResponse> addNote(
            @PathVariable UUID libraryId,
            @Valid @RequestBody LibraryNoteRequest request
    ) {
        LibraryNote savedNote = addNoteUseCase.execute(libraryId, request);

        LibraryNoteResponse responseDto = mapper.toNoteResponse(savedNote);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping("/{libraryId}/notes")
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