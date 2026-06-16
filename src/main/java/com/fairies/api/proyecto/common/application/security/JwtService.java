package com.fairies.api.proyecto.common.application.security;

import java.util.UUID;

public interface JwtService {
    String generateToken(UUID userId, String role);
    boolean validateToken(String token);
    String getRoleFromToken(String token);
    UUID getUserIdFromToken(String token);
}