package com.fairies.api.proyecto.modules.user.domain.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "pictures")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Picture {

    @Id
    private Long id;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private boolean deleted = false;
}