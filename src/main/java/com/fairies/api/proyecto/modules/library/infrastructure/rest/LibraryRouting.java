package com.fairies.api.proyecto.modules.library.infrastructure.rest;

import com.fairies.api.proyecto.common.application.security.JwtService;
import com.fairies.api.proyecto.modules.book.application.AddBookUseCase;
import com.fairies.api.proyecto.modules.book.domain.model.Book;
import com.fairies.api.proyecto.modules.library.application.*;
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
    private final AddBookUseCase addBookUseCase;
    private final GetAllLibraryUseCase getAllUseCase;
    private final UpdateLibraryUseCase updateUseCase;
    private final DeleteLibraryUseCase deleteUseCase;
    private final GetByIdUserUseCase getByIdUserUseCase;

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

    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(
            @PathVariable UUID id,
            @RequestBody UpdateLibraryEntryRequest request,
            @RequestHeader("Authorization") String authHeader
    ) {
        UUID userId = jwtService.getUserIdFromToken(authHeader);
        updateUseCase.execute(userId, id, request);
        return ResponseEntity.noContent().build();
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
}