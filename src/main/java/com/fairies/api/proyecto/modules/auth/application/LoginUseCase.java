package com.fairies.api.proyecto.modules.auth.application;

import com.fairies.api.proyecto.common.application.security.PasswordHasher;
import com.fairies.api.proyecto.modules.user.domain.model.User;
import com.fairies.api.proyecto.modules.user.infrastructure.persistence.UserRepository;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class LoginUseCase {

    private final UserRepository userRepository;
    private final PasswordHasher passwordHasher;

    public LoginUseCase(UserRepository userRepository, PasswordHasher passwordHasher) {
        this.userRepository = userRepository;
        this.passwordHasher = passwordHasher;
    }

    public User execute(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AuthenticationCredentialsNotFoundException("Credenciales incorrectas."));

        if (!passwordHasher.check(password, user.getPassword())) {
            throw new AuthenticationCredentialsNotFoundException("Credenciales incorrectas.");
        }

        return user;
    }
}