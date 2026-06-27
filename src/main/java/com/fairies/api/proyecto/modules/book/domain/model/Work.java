package com.fairies.api.proyecto.modules.book.domain.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import java.util.UUID;

@Entity
@Table(name = "works")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Work {

    @Id
    @GeneratedValue(generator = "uuid-v7")
    @UuidGenerator(style = UuidGenerator.Style.VERSION_7)
    private UUID id;

    @Column(nullable = false)
    private String originalTitle;
}