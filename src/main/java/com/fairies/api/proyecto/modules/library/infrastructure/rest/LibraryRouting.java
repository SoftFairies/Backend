package com.fairies.api.proyecto.modules.library.infrastructure.rest;

import com.fairies.api.proyecto.common.application.security.JwtService;
import com.fairies.api.proyecto.modules.book.application.AddBookUseCase;
import com.fairies.api.proyecto.modules.book.domain.model.Book;
import com.fairies.api.proyecto.modules.library.application.*;
import com.fairies.api.proyecto.modules.library.domain.model.UserBookCustomization;
import com.fairies.api.proyecto.modules.library.domain.model.UserLibrary;
import com.fairies.api.proyecto.modules.library.infrastructure.rest.dto.BookCustomizationRequest;
import com.fairies.api.proyecto.modules.library.infrastructure.rest.dto.LibraryEnrollmentRequest;
import com.fairies.api.proyecto.modules.library.infrastructure.rest.dto.LibraryProgressRequest;
import com.fairies.api.proyecto.modules.library.infrastructure.rest.mapper.LibraryMapper;
import lombok.RequiredArgsConstructor;
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
    private final SaveCustomizationUseCase saveCustomizationUseCase;

    private final JwtService jwtService;
    private final LibraryMapper mapper;

    @PostMapping
    public ResponseEntity<Void> add(
            @RequestBody LibraryEnrollmentRequest request,
            @RequestHeader("Authorization") String authHeader
    ) {
        UUID userId = jwtService.getUserIdFromToken(authHeader);
        UUID bookId;

        // 1. Obtener o crear el libro
        if (request.bookId() != null) {
            bookId = request.bookId();
        } else {
            Book newBook = addBookUseCase.execute(mapper.toBook(request.bookData()));
            bookId = newBook.getId();
        }

        CreateLibraryEntryCommand command = new CreateLibraryEntryCommand(
                userId,
                bookId,
                request.readingStatusId(),
                request.currentPage()
        );

        addUseCase.execute(command);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<UserLibrary>> getAll(
            @RequestHeader("Authorization") String authHeader,
            Pageable pageable
    ) {
        UUID userId = jwtService.getUserIdFromToken(authHeader);
        return ResponseEntity.ok(getAllUseCase.execute(userId, pageable));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(
            @PathVariable UUID id,
            @RequestBody LibraryProgressRequest request,
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

    @PostMapping("/{id}/customization")
    public ResponseEntity<UserBookCustomization> customize(
            @PathVariable UUID id,
            @RequestBody BookCustomizationRequest request,
            @RequestHeader("Authorization") String authHeader
    ) {
        UUID userId = jwtService.getUserIdFromToken(authHeader);
        UserBookCustomization result = saveCustomizationUseCase.execute(userId, id, request);
        return ResponseEntity.ok(result);
    }
}