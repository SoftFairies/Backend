package com.fairies.api.proyecto.modules.readingsession.domain.model;

import com.fairies.api.proyecto.modules.library.domain.model.UserLibrary;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "reading_sessions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReadingSession {

    @Id
    @GeneratedValue(generator = "uuid-v7")
    @UuidGenerator(style = UuidGenerator.Style.VERSION_7)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_library_id", nullable = false)
    private UserLibrary userLibrary;

    @Column(nullable = false)
    private LocalDate date;

    @Column(name = "seconds_read", nullable = false)
    private Integer secondsRead;

    @Column(name = "pages_read", nullable = false)
    private Integer pagesRead;

    @Column(name = "chapters_read", nullable = false)
    private Integer chaptersRead;

    @Column(name = "is_flagged", nullable = false)
    @Builder.Default
    private boolean isFlagged = false;

    @Column(name = "flag_reason")
    private String flagReason;
}