package com.fairies.api.proyecto.modules.streak.infrastructure.rest;

import com.fairies.api.proyecto.common.application.security.JwtService;
import com.fairies.api.proyecto.modules.streak.application.GetUserStreakUseCase;
import com.fairies.api.proyecto.modules.streak.infrastructure.rest.dto.UserStreakResponse;
import com.fairies.api.proyecto.modules.streak.infrastructure.rest.mapper.StreakMapper;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/streaks")
@RequiredArgsConstructor
public class StreakRouting {

    private final JwtService jwtService;
    private final GetUserStreakUseCase getUserStreakUseCase;
    private final StreakMapper streakMapper;

    @GetMapping("/me")
    @Operation(summary = "Obtiene la racha de actividad del usuario autenticado")
    public ResponseEntity<UserStreakResponse> getMyStreak(
            @RequestHeader("Authorization") String authHeader
    ) {
        UUID userId = jwtService.getUserIdFromToken(authHeader);

        // 2. Buscamos la racha y la devolvemos
        return getUserStreakUseCase.execute(userId)
                .map(streakMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.ok(new UserStreakResponse(null, 0, 0, null)));
    }
}