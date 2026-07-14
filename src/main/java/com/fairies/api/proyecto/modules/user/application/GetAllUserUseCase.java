package com.fairies.api.proyecto.modules.user.application;

import com.fairies.api.proyecto.modules.user.domain.model.User;
import com.fairies.api.proyecto.modules.user.infrastructure.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class GetAllUserUseCase {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public Page<User> execute(Pageable pageable) {
        return userRepository.findAll(pageable);
    }
}