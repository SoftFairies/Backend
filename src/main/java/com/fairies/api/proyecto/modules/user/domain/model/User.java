package com.fairies.api.proyecto.modules.user.domain.model;

import com.fairies.api.proyecto.modules.picture.domain.model.Picture;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@SQLDelete(sql = "UPDATE users SET deleted = true, deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted = false")
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "picture_id", nullable = true)
    private Picture profilePicture;

    @Column(nullable = false)
    private boolean deleted = false;

    private LocalDateTime deletedAt;

}