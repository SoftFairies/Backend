package com.fairies.api.proyecto.modules.user.application;

import com.fairies.api.proyecto.modules.user.domain.model.User;
import com.fairies.api.proyecto.modules.user.infrastructure.persistence.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class GetAllUserUseCase {

    private final UserRepository userRepository;

    public GetAllUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Page<User> execute(Pageable pageable) {
        return userRepository.findAll(pageable);
    }
}