package com.fairies.api.proyecto.modules.book.infrastructure.rest;

import com.fairies.api.proyecto.modules.book.application.*;
import com.fairies.api.proyecto.modules.book.domain.model.Book;
import com.fairies.api.proyecto.modules.book.infrastructure.rest.dto.BookRequest;
import com.fairies.api.proyecto.modules.book.infrastructure.rest.dto.BookResponse;
import com.fairies.api.proyecto.modules.book.infrastructure.rest.mapper.BookMapper;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/books")
public class BookRouting {

    private final AddBookUseCase addUseCase;
    private final GetAllBookUseCase getAllUseCase;
    private final GetByIdBookUseCase getByIdUseCase;
    private final DeleteBookUseCase deleteUseCase;
    private final UpdateBookUseCase updateUseCase;
    private final BookMapper mapper;

    public BookRouting(
            AddBookUseCase addUseCase,
            GetAllBookUseCase getAllUseCase,
            GetByIdBookUseCase getByIdUseCase,
            DeleteBookUseCase deleteUseCase,
            UpdateBookUseCase updateUseCase,
            BookMapper mapper
    ) {
        this.addUseCase = addUseCase;
        this.getAllUseCase = getAllUseCase;
        this.getByIdUseCase = getByIdUseCase;
        this.deleteUseCase = deleteUseCase;
        this.updateUseCase = updateUseCase;
        this.mapper = mapper;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Creates a new book")
    public BookResponse create(@Valid @RequestBody BookRequest request) {
        Book domain = mapper.toDomain(request);
        Book saved = addUseCase.execute(domain);
        return mapper.toResponse(saved);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get book by ID")
    public BookResponse getById(@PathVariable UUID id) {
        Book entity = getByIdUseCase.execute(id);
        return mapper.toResponse(entity);
    }

    @GetMapping
    @Operation(summary = "Get all books paginated")
    public Page<BookResponse> getAll(@PageableDefault(size = 10) Pageable pageable) {
        return getAllUseCase.execute(pageable).map(mapper::toResponse);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update book by ID")
    public BookResponse update(@PathVariable UUID id, @Valid @RequestBody BookRequest request) {
        Book domain = mapper.toDomain(request);
        Book updated = updateUseCase.execute(id, domain);
        return mapper.toResponse(updated);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete book by ID")
    public void delete(@PathVariable UUID id) {
        deleteUseCase.execute(id);
    }
}