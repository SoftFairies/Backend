package com.fairies.api.proyecto.modules.catalog.domain.models;

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
    private Long id;

    @Column(nullable = false)
    private String name;
}
