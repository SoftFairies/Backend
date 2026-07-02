// feat(mailbox): simplify letter model to represent a basic user-claimed letter log
package com.fairies.api.proyecto.modules.mailbox.domain.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "letters")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Letter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private UUID receiverId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recommendation_content_id", nullable = false)
    private RecommendationContent recommendationContent;

    @Column(nullable = false)
    private LocalDateTime claimedAt;
}