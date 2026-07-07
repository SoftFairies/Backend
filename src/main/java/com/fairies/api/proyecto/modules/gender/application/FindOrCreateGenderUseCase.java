package com.fairies.api.proyecto.modules.gender.application;

import com.fairies.api.proyecto.modules.gender.domain.model.Gender;
import com.fairies.api.proyecto.modules.gender.infrastructure.persistence.GenderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class FindOrCreateGenderUseCase {

    private final GenderRepository genderRepository;

    @Transactional
    public Gender execute(String name) {
        String cleanName = name.trim();
        return genderRepository.findByNameIgnoreCase(cleanName)
                .orElseGet(() -> {
                    Gender newGender = new Gender();
                    newGender.setName(cleanName);
                    return genderRepository.save(newGender);
                });
    }
}