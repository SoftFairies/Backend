package com.fairies.api.proyecto.modules.user.application;

import com.fairies.api.proyecto.common.infrastructure.rest.exception.ResourceNotFoundException;
import com.fairies.api.proyecto.modules.user.domain.model.Picture;
import com.fairies.api.proyecto.modules.user.domain.model.User;
import com.fairies.api.proyecto.modules.user.infrastructure.persistence.PictureRepository;
import com.fairies.api.proyecto.modules.user.infrastructure.persistence.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class UpdateUserUseCase {

    private final UserRepository userRepository;
    private final PictureRepository pictureRepository;

    public UpdateUserUseCase(UserRepository userRepository, PictureRepository pictureRepository) {
        this.userRepository = userRepository;
        this.pictureRepository = pictureRepository;
    }

    public User execute(User user) {
        if (user.getProfilePicture() != null && user.getProfilePicture().getId() != null) {
            Long pictureId = user.getProfilePicture().getId();

            Picture picture = pictureRepository.findById(pictureId)
                    .orElseThrow(() -> new ResourceNotFoundException("La imagen especificada no existe (ID: " + pictureId + ")"));

            if (picture.isDeleted()) {
                throw new IllegalArgumentException("La imagen seleccionada ya no se encuentra disponible.");
            }

            user.setProfilePicture(picture);
        }

        return userRepository.save(user);
    }
}