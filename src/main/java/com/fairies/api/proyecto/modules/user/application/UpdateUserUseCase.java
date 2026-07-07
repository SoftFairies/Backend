package com.fairies.api.proyecto.modules.user.application;

import com.fairies.api.proyecto.common.application.security.PasswordHasher;
import com.fairies.api.proyecto.common.infrastructure.rest.exception.ResourceNotFoundException;
import com.fairies.api.proyecto.modules.picture.domain.model.Picture;
import com.fairies.api.proyecto.modules.picture.infrastructure.persistence.PictureRepository;
import com.fairies.api.proyecto.modules.user.domain.model.User;
import com.fairies.api.proyecto.modules.user.infrastructure.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class UpdateUserUseCase {

    private final UserRepository userRepository;
    private final PictureRepository pictureRepository;
    private final PasswordHasher passwordHasher;

    @Transactional
    public User execute(User user, Long pictureId, String rawPassword) {

        if (pictureId != null) {
            Picture picture = pictureRepository.findById(pictureId)
                    .orElseThrow(() -> new ResourceNotFoundException("La imagen no existe"));

            if (picture.isDeleted()) {
                throw new IllegalArgumentException("La imagen seleccionada ya no está disponible.");
            }

            user.setProfilePicture(picture);
        }

        if (rawPassword != null && !rawPassword.isBlank()) {
            user.setPassword(passwordHasher.hash(rawPassword));
        }

        return userRepository.save(user);
    }
}