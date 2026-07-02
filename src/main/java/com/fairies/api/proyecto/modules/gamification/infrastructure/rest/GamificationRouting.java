package com.fairies.api.proyecto.modules.gamification.infrastructure.rest;

import com.fairies.api.proyecto.common.application.security.JwtService;
import com.fairies.api.proyecto.modules.gamification.application.GetUserBadgesUseCase;
import com.fairies.api.proyecto.modules.gamification.domain.model.UserBadge;
import com.fairies.api.proyecto.modules.gamification.infrastructure.rest.dto.UserBadgeResponse;
import com.fairies.api.proyecto.modules.gamification.infrastructure.rest.mapper.UserBadgeMapper;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/gamification")
@RequiredArgsConstructor
public class GamificationRouting {

    private final GetUserBadgesUseCase getUserBadgesUseCase;
    private final JwtService jwtService;
    private final UserBadgeMapper userBadgeMapper;

    @Operation(summary = "Obtener insignias del usuario")
    @GetMapping("/me/badges")
    public ResponseEntity<List<UserBadgeResponse>> getMyBadges(@RequestHeader("Authorization") String authHeader) {
        UUID authenticatedUserId = jwtService.getUserIdFromToken(authHeader);
        List<UserBadge> domainBadges = getUserBadgesUseCase.execute(authenticatedUserId);
        List<UserBadgeResponse> response = domainBadges.stream()
                .map(userBadgeMapper::toResponse)
                .toList();

        return ResponseEntity.ok(response);
    }
}