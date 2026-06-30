package com.fairies.api.proyecto.modules.format.domain.model;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "formats")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Format {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;
}
