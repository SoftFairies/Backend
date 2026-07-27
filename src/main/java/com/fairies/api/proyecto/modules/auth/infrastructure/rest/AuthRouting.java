package com.fairies.api.proyecto.modules.auth.infrastructure.rest;

import com.fairies.api.proyecto.common.application.security.JwtService;
import com.fairies.api.proyecto.modules.auth.application.LoginUseCase;
import com.fairies.api.proyecto.modules.auth.application.RegisterUseCase;
import com.fairies.api.proyecto.modules.auth.application.VerifyTwoFactorUseCase;
import com.fairies.api.proyecto.modules.auth.infrastructure.rest.dto.AuthResponse;
import com.fairies.api.proyecto.modules.auth.infrastructure.rest.dto.LoginRequest;
import com.fairies.api.proyecto.modules.auth.infrastructure.rest.dto.RegisterRequest;
import com.fairies.api.proyecto.modules.auth.infrastructure.rest.dto.VerifyTwoFactorRequest;
import com.fairies.api.proyecto.modules.user.domain.model.User;
import com.fairies.api.proyecto.modules.user.infrastructure.rest.mapper.UserMapper;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthRouting {
    private final RegisterUseCase registerUseCase;
    private final LoginUseCase loginUseCase;
    private final VerifyTwoFactorUseCase verifyTwoFactorUseCase;
    private final JwtService jwtService;
    private final UserMapper userMapper;

    @PostMapping("/register")
    @Transactional
    @Operation(summary = "Registra un nuevo usuario en la plataforma")
    public AuthResponse register(@Valid @RequestBody RegisterRequest request) {
        User userDomain = userMapper.toDomain(request);
        User savedUser = registerUseCase.execute(userDomain);
        String token = jwtService.generateToken(savedUser.getId(), savedUser.getRole().getName());
        return new AuthResponse(
                savedUser.getId(),
                savedUser.getEmail(),
                savedUser.getRole().getName(),
                token,
                false
        );
    }

    @PostMapping("/login")
    @Operation(summary = "Inicia sesión y obtiene credenciales de acceso o solicita verificación 2FA")
    public AuthResponse login(@Valid @RequestBody LoginRequest request) {
        var loginResult = loginUseCase.execute(request.email(), request.password());
        User user = loginResult.user();

        if (loginResult.requiresTwoFactor()) {
            return new AuthResponse(
                    user.getId(),
                    user.getEmail(),
                    user.getRole().getName(),
                    null,
                    true
            );
        }

        String token = jwtService.generateToken(user.getId(), user.getRole().getName());
        return new AuthResponse(
                user.getId(),
                user.getEmail(),
                user.getRole().getName(),
                token,
                false
        );
    }

    @PostMapping("/verify-2fa")
    @Operation(summary = "Verifica el código 2FA enviado por correo y devuelve las credenciales de acceso")
    public AuthResponse verifyTwoFactor(@Valid @RequestBody VerifyTwoFactorRequest request) {
        User user = verifyTwoFactorUseCase.execute(request.email(), request.code());
        String token = jwtService.generateToken(user.getId(), user.getRole().getName());
        return new AuthResponse(
                user.getId(),
                user.getEmail(),
                user.getRole().getName(),
                token,
                false
        );
    }
}