package com.fairies.api.proyecto.modules.readingsession.infrastructure.rest;

import com.fairies.api.proyecto.common.application.security.JwtService;
import com.fairies.api.proyecto.modules.readingsession.aplication.AddReadingSessionUseCase;
import com.fairies.api.proyecto.modules.readingsession.infrastructure.rest.dto.ReadingSessionRequest;
import com.fairies.api.proyecto.modules.readingsession.infrastructure.rest.dto.ReadingSessionResponse;
import com.fairies.api.proyecto.modules.readingsession.infrastructure.rest.mapper.ReadingSessionMapper;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/reading-sessions")
@RequiredArgsConstructor
public class ReadingSessionRouting {

    private final AddReadingSessionUseCase addUseCase;
    private final JwtService jwtService;
    private final ReadingSessionMapper mapper;

    @PostMapping("/{libraryId}")
    @Operation(summary = "Registra una nueva sesión de lectura en una biblioteca específica")
    public ResponseEntity<ReadingSessionResponse> create(
            @PathVariable UUID libraryId,
            @Valid @RequestBody ReadingSessionRequest request,
            @RequestHeader("Authorization") String authHeader) {

        UUID userId = jwtService.getUserIdFromToken(authHeader);
        var savedSession = addUseCase.execute(userId, libraryId, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(savedSession));
    }
}