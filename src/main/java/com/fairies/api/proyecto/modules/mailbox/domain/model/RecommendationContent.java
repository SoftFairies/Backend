// feat(mailbox): create recommendation content entity to avoid database text saturation
package com.fairies.api.proyecto.modules.mailbox.domain.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Table(name = "recommendation_contents")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecommendationContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private UUID bookId;

    @Column(nullable = false)
    private UUID senderId;

    @Column(nullable = false, length = 2000)
    private String content;
}