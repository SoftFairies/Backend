package com.fairies.api.proyecto.modules.auth.application;

import com.fairies.api.proyecto.common.application.security.PasswordHasher;
import com.fairies.api.proyecto.common.domain.service.EmailService;
import com.fairies.api.proyecto.modules.gamification.application.AwardBadgeUseCase;
import com.fairies.api.proyecto.modules.user.domain.model.User;
import com.fairies.api.proyecto.modules.user.infrastructure.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class LoginUseCase {
    private final UserRepository userRepository;
    private final PasswordHasher passwordHasher;
    private final AwardBadgeUseCase awardBadgeUseCase;
    private final EmailService emailService;

    @Transactional
    public LoginResult execute(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AuthenticationCredentialsNotFoundException("Credenciales incorrectas."));

        if (!passwordHasher.check(password, user.getPassword())) {
            throw new AuthenticationCredentialsNotFoundException("Credenciales incorrectas.");
        }

        if (user.isTwoFactorEnabled()) {
            String otpCode = String.format("%06d", new SecureRandom().nextInt(1000000));
            user.setTwoFactorCode(otpCode);
            user.setTwoFactorCodeExpiresAt(LocalDateTime.now().plusMinutes(10));
            userRepository.save(user);

            emailService.sendOtpEmail(user.getEmail(), otpCode);

            return new LoginResult(user, true);
        }

        awardBadgeUseCase.execute(user.getId(), 8L);
        return new LoginResult(user, false);
    }

    public record LoginResult(User user, boolean requiresTwoFactor) {}
}