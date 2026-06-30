package com.fairies.api.proyecto.common.infrastructure.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.fairies.api.proyecto.common.application.security.JwtService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public class Auth0JwtService implements JwtService {

    @Value("${JWT_SECRET}")
    private String secretKey;

    private final long EXPIRATION_TIME = 86_400_000;

    @Override
    public String generateToken(UUID userId, String role) {
        return JWT.create()
                .withSubject(userId.toString())
                .withClaim("role", role)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC256(secretKey));
    }

    @Override
    public boolean validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            JWTVerifier verifier = JWT.require(algorithm).build();
            verifier.verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String getRoleFromToken(String token) {
        DecodedJWT decodedJWT = JWT.decode(token);
        return decodedJWT.getClaim("role").asString();
    }

    @Override
    public UUID getUserIdFromToken(String token) {
        String rawToken = cleanToken(token);
        DecodedJWT decodedJWT = JWT.decode(rawToken);
        String userIdStr = decodedJWT.getSubject();
        return UUID.fromString(userIdStr);
    }

    private String cleanToken(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            return token.substring(7).trim();
        }
        return token != null ? token.trim() : "";
    }
}