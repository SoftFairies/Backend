package com.fairies.api.proyecto.modules.user.infrastructure.rest;

import com.fairies.api.proyecto.common.application.security.JwtService;
import com.fairies.api.proyecto.modules.auth.infrastructure.rest.dto.AuthResponse;
import com.fairies.api.proyecto.modules.auth.infrastructure.rest.dto.RegisterRequest;
import com.fairies.api.proyecto.modules.user.application.*;
import com.fairies.api.proyecto.modules.user.domain.model.User;
import com.fairies.api.proyecto.modules.user.infrastructure.rest.dto.UpdateUserRequest;
import com.fairies.api.proyecto.modules.user.infrastructure.rest.dto.UserResponse;
import com.fairies.api.proyecto.modules.user.infrastructure.rest.mapper.UserMapper;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
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
    private final UpdateUserUseCase updateUseCase;
    private final DeleteUserUseCase deleteUseCase;
    private final JwtService jwtService;
    private final UserMapper userMapper;

    public UserRouting(
            AddUserUseCase addUserUseCase,
            GetAllUserUseCase getAllUserUseCase,
            GetByIdUserUseCase getByIdUserUseCase,
            UpdateUserUseCase updateUseCase,
            DeleteUserUseCase deleteUseCase,
            JwtService jwtService,
            UserMapper userMapper
    ) {
        this.addUserUseCase = addUserUseCase;
        this.getAllUserUseCase = getAllUserUseCase;
        this.getByIdUserUseCase = getByIdUserUseCase;
        this.updateUseCase = updateUseCase;
        this.deleteUseCase = deleteUseCase;
        this.jwtService = jwtService;
        this.userMapper = userMapper;
    }

    @PostMapping
    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Creates new user")
    public AuthResponse register(@Valid @RequestBody RegisterRequest request) {
        User userDomain = userMapper.toDomain(request);
        User savedUser = addUserUseCase.execute(userDomain);
        String token = jwtService.generateToken(savedUser.getId(), savedUser.getRole().getName());

        return new AuthResponse(
                savedUser.getId(),
                savedUser.getEmail(),
                savedUser.getRole().getName(),
                token
        );
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

    @PutMapping("/me")
    @Transactional
    @Operation(summary = "Updates the currently authenticated user details via JWT token")
    public UserResponse updateMe(
            @RequestHeader("Authorization") String authHeader,
            @Valid @RequestBody UpdateUserRequest request
    ) {
        UUID authenticatedUserId = jwtService.getUserIdFromToken(authHeader);

        User userToUpdate = getByIdUserUseCase.execute(authenticatedUserId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        userMapper.updateFromRequest(request, userToUpdate);
        User updatedUser = updateUseCase.execute(userToUpdate, request.pictureId());
        return userMapper.toResponse(updatedUser);
    }

    @DeleteMapping("/me")
    @Transactional
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Deletes the currently authenticated user account via JWT token")
    public void deleteMe(@RequestHeader("Authorization") String authHeader) {
        UUID authenticatedUserId = jwtService.getUserIdFromToken(authHeader);
        deleteUseCase.execute(authenticatedUserId);
    }

    @GetMapping("/me")
    @Operation(summary = "Get user by ID")
    public UserResponse getById(@RequestHeader("Authorization") String authHeader) {
        UUID authenticatedUserId = jwtService.getUserIdFromToken(authHeader);
        return getByIdUserUseCase.execute(authenticatedUserId)
                .map(userMapper::toResponse)
                .orElseThrow(() -> new IllegalArgumentException("Your user not found"));
    }

}