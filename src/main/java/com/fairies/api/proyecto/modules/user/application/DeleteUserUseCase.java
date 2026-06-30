package com.fairies.api.proyecto.modules.user.application;

import com.fairies.api.proyecto.common.infrastructure.rest.exception.ResourceNotFoundException;
import com.fairies.api.proyecto.modules.user.infrastructure.persistence.UserRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class DeleteUserUseCase {

    private final UserRepository userRepository;

    public DeleteUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void execute(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("El ID del usuario no puede ser nulo.");
        }

        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with ID: " + id);
        }

        userRepository.deleteById(id);
    }
}