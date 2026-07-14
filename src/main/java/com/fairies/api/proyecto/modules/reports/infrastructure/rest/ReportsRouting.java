package com.fairies.api.proyecto.modules.reports.infrastructure.rest;

import com.fairies.api.proyecto.common.application.security.JwtService;
import com.fairies.api.proyecto.modules.reports.application.GetDashboardMetricsUseCase;
import com.fairies.api.proyecto.modules.reports.infrastructure.rest.dto.DashboardMetricsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/reports")
@RequiredArgsConstructor
public class ReportsRouting {

    private final GetDashboardMetricsUseCase getDashboardMetricsUseCase;
    private final JwtService jwtService;

    @GetMapping("/dashboard")
    public ResponseEntity<DashboardMetricsResponse> getDashboard(
            @RequestHeader("Authorization") String authHeader
    ) {
        UUID userId = jwtService.getUserIdFromToken(authHeader);
        DashboardMetricsResponse metrics = getDashboardMetricsUseCase.execute(userId);
        return ResponseEntity.ok(metrics);
    }
}