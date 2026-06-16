package com.fairies.api.proyecto.modules.auth.infrastructure.rest;

import com.fairies.api.proyecto.modules.auth.application.LoginUseCase;
import com.fairies.api.proyecto.modules.auth.application.RegisterUseCase;
import com.fairies.api.proyecto.modules.auth.infrastructure.rest.dto.AuthResponse;
import com.fairies.api.proyecto.modules.auth.infrastructure.rest.dto.LoginRequest;
import com.fairies.api.proyecto.modules.auth.infrastructure.rest.dto.RegisterRequest;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthRouting {

    private final RegisterUseCase registerUseCase;
    private final LoginUseCase loginUseCase;

    public AuthRouting(RegisterUseCase registerUseCase, LoginUseCase loginUseCase) {
        this.registerUseCase = registerUseCase;
        this.loginUseCase = loginUseCase;
    }

    @PostMapping("/register")
    @Transactional
    @Operation(summary = "Registra un nuevo usuario en la plataforma")
    public AuthResponse register(@Valid @RequestBody RegisterRequest request) {
        return registerUseCase.execute(request);
    }

    @PostMapping("/login")
    @Operation(summary = "Inicia sesión y obtiene credenciales de acceso")
    public AuthResponse login(@Valid @RequestBody LoginRequest request) {
        return loginUseCase.execute(request);
    }
}