package com.fairies.api.proyecto.modules.auth.application;

import com.fairies.api.proyecto.common.application.security.PasswordHasher;
import com.fairies.api.proyecto.modules.gamification.application.AwardBadgeUseCase;
import com.fairies.api.proyecto.modules.user.domain.model.Role;
import com.fairies.api.proyecto.modules.user.domain.model.User;
import com.fairies.api.proyecto.modules.user.infrastructure.persistence.RoleRepository;
import com.fairies.api.proyecto.modules.user.infrastructure.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RegisterUseCase {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordHasher passwordHasher;
    private final AwardBadgeUseCase awardBadgeUseCase;

    public User execute(User newUser) {
        if (userRepository.findByEmail(newUser.getEmail()).isPresent()) {
            throw new IllegalArgumentException("El correo electrónico ya está en uso.");
        }

        Role role = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new IllegalStateException("El rol por defecto 'USER' no existe en la base de datos."));

        newUser.setPassword(passwordHasher.hash(newUser.getPassword()));
        newUser.setRole(role);

        User savedUser = userRepository.save(newUser);

        awardBadgeUseCase.execute(savedUser.getId(), 8L);

        return savedUser;
    }
}