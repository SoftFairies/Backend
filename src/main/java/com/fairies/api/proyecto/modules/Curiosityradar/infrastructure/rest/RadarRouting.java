package com.fairies.api.proyecto.modules.Curiosityradar.infrastructure.rest;

import com.fairies.api.proyecto.common.application.security.JwtService;
import com.fairies.api.proyecto.modules.Curiosityradar.aplication.GetPersonalizedCuriosityUseCase;
import com.fairies.api.proyecto.modules.Curiosityradar.domain.model.Curiosity;
import com.fairies.api.proyecto.modules.Curiosityradar.infrastructure.rest.dto.CuriosityResponse;
import com.fairies.api.proyecto.modules.Curiosityradar.infrastructure.rest.mapper.CuriosityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/radar")
@RequiredArgsConstructor
public class RadarRouting {

    private final GetPersonalizedCuriosityUseCase getPersonalizedCuriosityUseCase;
    private final JwtService jwtService;
    private final CuriosityMapper mapper;

    @GetMapping("/random")
    @PreAuthorize("isAuthenticated()") // Permite que cualquier lector autenticado consulte el radar
    public ResponseEntity<CuriosityResponse> getRandomCuriosity(
            @RequestHeader("Authorization") String authHeader
    ) {
        UUID userId = jwtService.getUserIdFromToken(authHeader);
        Curiosity curiosity = getPersonalizedCuriosityUseCase.execute(userId);
        return ResponseEntity.ok(mapper.toResponse(curiosity));
    }
}