package com.fairies.api.proyecto.modules.user.domain.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(generator = "uuid-v7")
    @UuidGenerator(style = UuidGenerator.Style.VERSION_7)
    private UUID id;

    private String name;

    private String lastname;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @Column(nullable = false, length = 100, unique = true)
    private String email;

    @Column(nullable = false, length = 255)
    private String password;

}