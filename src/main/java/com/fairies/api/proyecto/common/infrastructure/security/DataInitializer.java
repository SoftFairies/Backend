package com.fairies.api.proyecto.common.infrastructure.security;

import com.fairies.api.proyecto.modules.user.domain.model.Role;
import com.fairies.api.proyecto.modules.user.infrastructure.persistence.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    public DataInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        List<String> defaultRoles = List.of("ROLE_ADMIN", "ROLE_USER");

        for (String roleName : defaultRoles) {
            if (roleRepository.findByName(roleName).isEmpty()) {
                Role newRole = Role.builder()
                        .name(roleName)
                        .build();

                roleRepository.save(newRole);
                System.out.println("-> Rol inicializado con éxito: " + roleName);
            }
        }
    }
}