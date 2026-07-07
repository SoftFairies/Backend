package com.fairies.api.proyecto.modules.auth.application;

import com.fairies.api.proyecto.common.application.security.PasswordHasher;
import com.fairies.api.proyecto.modules.gamification.application.AwardBadgeUseCase;
import com.fairies.api.proyecto.modules.user.domain.model.User;
import com.fairies.api.proyecto.modules.user.infrastructure.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class LoginUseCase {

    private final UserRepository userRepository;
    private final PasswordHasher passwordHasher;
    private final AwardBadgeUseCase awardBadgeUseCase;

    @Transactional
    public User execute(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AuthenticationCredentialsNotFoundException("Credenciales incorrectas."));

        if (!passwordHasher.check(password, user.getPassword())) {
            throw new AuthenticationCredentialsNotFoundException("Credenciales incorrectas.");
        }

        awardBadgeUseCase.execute(user.getId(), 8L);

        return user;
    }
}