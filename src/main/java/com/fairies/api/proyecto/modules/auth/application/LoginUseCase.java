package com.fairies.api.proyecto.modules.auth.application;

import com.fairies.api.proyecto.common.application.security.JwtService;
import com.fairies.api.proyecto.modules.auth.infrastructure.rest.dto.AuthResponse;
import com.fairies.api.proyecto.modules.auth.infrastructure.rest.dto.LoginRequest;
import com.fairies.api.proyecto.modules.user.domain.model.User;
import com.fairies.api.proyecto.modules.user.infrastructure.persistence.UserRepository;
import com.fairies.api.proyecto.common.application.security.PasswordHasher;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class LoginUseCase {

    private final UserRepository userRepository;
    private final PasswordHasher passwordHasher;
    private final JwtService jwtService;

    public LoginUseCase(UserRepository userRepository, PasswordHasher passwordHasher, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordHasher = passwordHasher;
        this.jwtService = jwtService;
    }

    public AuthResponse execute(LoginRequest request) {

        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new AuthenticationCredentialsNotFoundException("Credenciales incorrectas."));

        if (!passwordHasher.check(request.password(), user.getPassword())) {
            throw new AuthenticationCredentialsNotFoundException("Credenciales incorrectas.");
        }

        String token = jwtService.generateToken(user.getId(), user.getRole().getName());

        return new AuthResponse(
                user.getId(),
                user.getEmail(),
                user.getRole().getName(),
                token
        );
    }
}