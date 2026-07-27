package com.fairies.api.proyecto.modules.auth.application;

import com.fairies.api.proyecto.common.infrastructure.rest.exception.ResourceNotFoundException;
import com.fairies.api.proyecto.modules.gamification.application.AwardBadgeUseCase;
import com.fairies.api.proyecto.modules.user.domain.model.User;
import com.fairies.api.proyecto.modules.user.infrastructure.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class VerifyTwoFactorUseCase {
    private final UserRepository userRepository;
    private final AwardBadgeUseCase awardBadgeUseCase;

    @Transactional
    public User execute(String email, String code) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado."));

        if (!user.isTwoFactorEnabled()) {
            throw new IllegalArgumentException("El usuario no tiene la autenticación de dos pasos activada.");
        }

        if (user.getTwoFactorCode() == null || !user.getTwoFactorCode().equals(code)) {
            throw new BadCredentialsException("Código de verificación incorrecto.");
        }

        if (user.getTwoFactorCodeExpiresAt() == null || LocalDateTime.now().isAfter(user.getTwoFactorCodeExpiresAt())) {
            throw new BadCredentialsException("El código de verificación ha expirado. Por favor, inicia sesión nuevamente.");
        }

        user.setTwoFactorCode(null);
        user.setTwoFactorCodeExpiresAt(null);
        userRepository.save(user);

        awardBadgeUseCase.execute(user.getId(), 8L);
        return user;
    }
}