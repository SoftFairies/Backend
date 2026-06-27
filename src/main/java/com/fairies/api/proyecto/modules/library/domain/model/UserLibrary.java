package com.fairies.api.proyecto.modules.library.domain.model;

import com.fairies.api.proyecto.modules.book.domain.model.Book;
import com.fairies.api.proyecto.modules.catalog.domain.models.ReadingStatus;
import com.fairies.api.proyecto.modules.user.domain.model.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import java.util.UUID;

@Entity
@Table(name = "user_library", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"user_id", "book_id"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserLibrary {

    @Id
    @GeneratedValue(generator = "uuid-v7")
    @UuidGenerator(style = UuidGenerator.Style.VERSION_7)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "reading_status_id", nullable = false)
    private ReadingStatus readingStatus;

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