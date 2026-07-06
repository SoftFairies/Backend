package com.fairies.api.proyecto.modules.library.domain.model;

import com.fairies.api.proyecto.modules.book.domain.model.Book;
import com.fairies.api.proyecto.modules.format.domain.model.Format;
import com.fairies.api.proyecto.modules.readingStatus.domain.model.ReadingStatus;
import com.fairies.api.proyecto.modules.user.domain.model.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.UUID;

@Entity
@Table(name = "user_library", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "book_id"})})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class UserLibrary {

    @Id
    @GeneratedValue(generator = "uuid-v7")
    @UuidGenerator(style = UuidGenerator.Style.VERSION_7)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reading_status_id", nullable = false)
    private ReadingStatus readingStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "format_id", nullable = false)
    private Format format;

    @Column(nullable = false)
    @Builder.Default
    private Integer currentChapter = 0;

    @Column(nullable = false)
    @Builder.Default
    private Integer currentPage = 0;

    @Column(nullable = false)
    @Builder.Default
    private boolean isFavorite = false;
}