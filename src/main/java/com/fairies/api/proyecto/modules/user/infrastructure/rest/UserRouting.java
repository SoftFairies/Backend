package com.fairies.api.proyecto.modules.user.infrastructure.rest;

import com.fairies.api.proyecto.modules.auth.infrastructure.rest.dto.AuthResponse;
import com.fairies.api.proyecto.modules.auth.infrastructure.rest.dto.RegisterRequest;
import com.fairies.api.proyecto.modules.user.application.*;
import com.fairies.api.proyecto.modules.user.infrastructure.rest.dto.UserResponse;
import com.fairies.api.proyecto.modules.user.infrastructure.rest.mapper.UserMapper;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/users")
public class UserRouting {

    private final AddUserUseCase addUserUseCase;
    private final GetAllUserUseCase getAllUserUseCase;
    private final GetByIdUserUseCase getByIdUserUseCase;
    private final UserMapper userMapper;

    public UserRouting(
            AddUserUseCase addUserUseCase,
            GetAllUserUseCase getAllUserUseCase,
            GetByIdUserUseCase getByIdUserUseCase,
            UserMapper userMapper
    ) {
        this.addUserUseCase = addUserUseCase;
        this.getAllUserUseCase = getAllUserUseCase;
        this.getByIdUserUseCase = getByIdUserUseCase;
        this.userMapper = userMapper;
    }

    @PostMapping
    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Creates new user")
    public AuthResponse register(@Valid @RequestBody RegisterRequest request) {
        return addUserUseCase.execute(request);
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Get all users with pagination")
    public Page<UserResponse> getAll(@PageableDefault(size = 10, page = 0) Pageable pageable) {
        return getAllUserUseCase.execute(pageable)
                .map(userMapper::toResponse);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Get user by ID")
    public UserResponse getById(@PathVariable UUID id) {
        return getByIdUserUseCase.execute(id)
                .map(userMapper::toResponse)
                .orElseThrow(() -> new IllegalArgumentException("User with ID " + id + " not found"));
    }

}