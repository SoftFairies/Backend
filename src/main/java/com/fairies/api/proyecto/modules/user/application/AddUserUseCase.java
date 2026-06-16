package com.fairies.api.proyecto.modules.user.application;

import com.fairies.api.proyecto.common.application.security.JwtService;
import com.fairies.api.proyecto.common.application.security.PasswordHasher;
import com.fairies.api.proyecto.modules.auth.infrastructure.rest.dto.AuthResponse;
import com.fairies.api.proyecto.modules.auth.infrastructure.rest.dto.RegisterRequest;
import com.fairies.api.proyecto.modules.user.domain.model.Role;
import com.fairies.api.proyecto.modules.user.domain.model.User;
import com.fairies.api.proyecto.modules.user.infrastructure.persistence.*;
import org.springframework.stereotype.Component;

@Component
public class AddUserUseCase {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordHasher passwordHasher;
    private final JwtService jwtService;

    public AddUserUseCase(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordHasher passwordHasher,
            JwtService jwtService
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordHasher = passwordHasher;
        this.jwtService = jwtService;
    }

    public AuthResponse execute(RegisterRequest request) {
        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new IllegalArgumentException("El correo electrónico ya esta en uso.");
        }

        Role role = (request.roleId() == null)
                ? roleRepository.findByName("ROLE_ADMIN").orElseThrow(() -> new IllegalStateException("El rol 'ADMIN' no existe."))
                : roleRepository.findById(request.roleId()).orElseThrow(() -> new IllegalStateException("El rol no existe."));

        String hashedPassword = passwordHasher.hash(request.password());

        User newUser = User.builder()
                .name(request.name())
                .lastname(request.lastname())
                .email(request.email())
                .password(hashedPassword)
                .role(role)
                .build();

        User savedUser = userRepository.save(newUser);

        String token = jwtService.generateToken(savedUser.getId(), savedUser.getRole().getName());

        return new AuthResponse(
                savedUser.getId(),
                savedUser.getEmail(),
                savedUser.getRole().getName(),
                token
        );
    }
}