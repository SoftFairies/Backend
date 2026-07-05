package com.fairies.api.proyecto.modules.user.application;

import com.fairies.api.proyecto.common.application.security.PasswordHasher;
import com.fairies.api.proyecto.modules.user.domain.model.Role;
import com.fairies.api.proyecto.modules.user.domain.model.User;
import com.fairies.api.proyecto.modules.user.infrastructure.persistence.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AddUserUseCase {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordHasher passwordHasher;

    public User execute(User newUser) {
        if (newUser == null || newUser.getEmail() == null || newUser.getEmail().isBlank()) {
            throw new IllegalArgumentException("Los datos de usuario son obligatorios.");
        }

        if (userRepository.findByEmail(newUser.getEmail()).isPresent()) {
            throw new IllegalArgumentException("El correo electrónico ya está en uso.");
        }

        UUID requestedRoleId = (newUser.getRole() != null) ? newUser.getRole().getId() : null;

        Role role = (requestedRoleId == null)
                ? roleRepository.findByName("ROLE_ADMIN").orElseThrow(() -> new IllegalStateException("El rol 'ADMIN' no existe."))
                : roleRepository.findById(requestedRoleId).orElseThrow(() -> new IllegalStateException("El rol no existe."));

        newUser.setPassword(passwordHasher.hash(newUser.getPassword()));
        newUser.setRole(role);

        return userRepository.save(newUser);
    }
}