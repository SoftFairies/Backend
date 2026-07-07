package com.fairies.api.proyecto.modules.book.infrastructure.rest;

import com.fairies.api.proyecto.modules.book.application.*;
import com.fairies.api.proyecto.modules.book.infrastructure.rest.dto.BookResponse;
import com.fairies.api.proyecto.modules.book.infrastructure.rest.dto.CreateBookRequest;
import com.fairies.api.proyecto.modules.book.infrastructure.rest.mapper.BookMapper;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/books")
public class BookRouting {

    private final AddBookUseCase addUseCase;
    private final SearchBookUseCase searchUseCase;
    private final GetAllBookUseCase getAllUseCase;
    private final GetByIdBookUseCase getByIdUseCase;
    private final DeleteBookUseCase deleteUseCase;
    private final BookMapper mapper;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Crear un nuevo libro manualmente en el catálogo global")
    @ResponseStatus(HttpStatus.CREATED)
    public BookResponse create(@Valid @RequestBody CreateBookRequest request) {
        return mapper.toResponse(addUseCase.execute(mapper.toDomain(request)));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get book by ID")
    public BookResponse getById(@PathVariable UUID id) {
        return mapper.toResponse(getByIdUseCase.execute(id));
    }

    @GetMapping
    @Operation(summary = "Get all books paginated (with optional search)")
    public Page<BookResponse> getAll(
            @RequestParam(required = false) String query,
            @PageableDefault(size = 10) Pageable pageable) {
        return getAllUseCase.execute(query, pageable).map(mapper::toResponse);
    }

    @GetMapping("/search")
    @Operation(summary = "Buscar libros (Caché local + API Externa)")
    public List<BookResponse> search(@RequestParam("q") String query) {
        return searchUseCase.execute(query).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Delete book by ID")
    public void delete(@PathVariable UUID id) {
        deleteUseCase.execute(id);
    }
}