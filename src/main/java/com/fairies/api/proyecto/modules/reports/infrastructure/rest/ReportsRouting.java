package com.fairies.api.proyecto.modules.reports.infrastructure.rest;

import com.fairies.api.proyecto.common.application.security.JwtService;
import com.fairies.api.proyecto.modules.reports.application.GetAdminDashboardUseCase;
import com.fairies.api.proyecto.modules.reports.application.GetDashboardMetricsUseCase;
import com.fairies.api.proyecto.modules.reports.infrastructure.rest.dto.AdminResponseDto;
import com.fairies.api.proyecto.modules.reports.infrastructure.rest.dto.DashboardMetricsResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/reports")
@RequiredArgsConstructor
public class ReportsRouting {

    private final GetDashboardMetricsUseCase getDashboardMetricsUseCase;
    private final GetAdminDashboardUseCase getAdminDashboardUseCase;
    private final JwtService jwtService;

    @GetMapping("/dashboard")
    @Operation(summary = "Obtiene las métricas generales, gráficas y progreso anual de lectura del usuario autenticado")
    public ResponseEntity<DashboardMetricsResponse> getDashboard(
            @RequestHeader("Authorization") String authHeader
    ) {
        UUID userId = jwtService.getUserIdFromToken(authHeader);
        DashboardMetricsResponse metrics = getDashboardMetricsUseCase.execute(userId);
        return ResponseEntity.ok(metrics);
    }

    @GetMapping("/overview")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Obtiene las métricas generales y la lista de usuarios reales excluyendo administradores")
    public ResponseEntity<AdminResponseDto> getAdminOverview() {
        return ResponseEntity.ok(getAdminDashboardUseCase.execute());
    }
}