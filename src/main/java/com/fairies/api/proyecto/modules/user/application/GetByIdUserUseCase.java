package com.fairies.api.proyecto.modules.user.application;

import com.fairies.api.proyecto.modules.user.domain.model.User;
import com.fairies.api.proyecto.modules.user.infrastructure.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GetByIdUserUseCase {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public Optional<User> execute(UUID id) {
        return userRepository.findById(id);
    }
}