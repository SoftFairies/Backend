package com.fairies.api.proyecto.modules.recommendation.application;

import com.fairies.api.proyecto.modules.format.domain.model.Format;
import com.fairies.api.proyecto.modules.format.infrastructure.persistence.FormatRepository;
import com.fairies.api.proyecto.modules.gender.domain.model.Gender;
import com.fairies.api.proyecto.modules.gender.infrastructure.persistence.GenderRepository;
import com.fairies.api.proyecto.modules.recommendation.domain.model.UserPreference;
import com.fairies.api.proyecto.modules.recommendation.infrastructure.persistence.UserPreferenceRepository;
import com.fairies.api.proyecto.modules.user.domain.model.User;
import com.fairies.api.proyecto.modules.user.infrastructure.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SavePreferenceUseCase {
    private final UserPreferenceRepository prefRepo;

    @Transactional
    public void execute(User user, Set<Format> formats, Set<Gender> genres) {
        UserPreference pref = prefRepo.findByUserId(user.getId())
                .orElse(UserPreference.builder().user(user).build());

        pref.setFormats(formats);
        pref.setGenres(genres);

        prefRepo.save(pref);
    }
}