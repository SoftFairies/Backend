package com.fairies.api.proyecto.modules.Curiosityradar.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "curiosities")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Curiosity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String fact;

    @Column(nullable = false)
    private String genre;

    @Column(name = "book_reference")
    private String bookReference;
}