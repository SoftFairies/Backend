package com.fairies.api.proyecto.modules.user.application;

import com.fairies.api.proyecto.common.infrastructure.rest.exception.ResourceNotFoundException;
import com.fairies.api.proyecto.modules.picture.domain.model.Picture;
import com.fairies.api.proyecto.modules.picture.infrastructure.persistence.PictureRepository;
import com.fairies.api.proyecto.modules.user.domain.model.User;
import com.fairies.api.proyecto.modules.user.infrastructure.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateUserUseCase {

    private final UserRepository userRepository;
    private final PictureRepository pictureRepository;

    public User execute(User user, Long pictureId) {

        if (pictureId != null) {
            Picture picture = pictureRepository.findById(pictureId)
                    .orElseThrow(() -> new ResourceNotFoundException("La imagen no existe"));

            if (picture.isDeleted()) {
                throw new IllegalArgumentException("La imagen seleccionada ya no está disponible.");
            }

            user.setProfilePicture(picture);
        }

        return userRepository.save(user);
    }
}