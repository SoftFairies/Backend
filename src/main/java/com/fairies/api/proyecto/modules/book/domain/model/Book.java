package com.fairies.api.proyecto.modules.book.domain.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "books")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
@SQLDelete(sql = "UPDATE books SET deleted = true, deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted = false")
public class Book {

    @Id
    @GeneratedValue(generator = "uuid-v7")
    @UuidGenerator(style = UuidGenerator.Style.VERSION_7)
    private UUID id;

//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "format_id", nullable = false)
//    private Format format;

    @Column(length = 50, unique = true)
    private String isbn;

    @Column(nullable = false)
    private String title;

    private Integer defaultChapters;
    private Integer defaultPages;

    @Column(nullable = false)
    private String origin;

    @Column(nullable = false)
    private String coverType;

    @Column(nullable = false)
    private String coverValue;

//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(
//            name = "book_authors",
//            joinColumns = @JoinColumn(name = "book_id"),
//            inverseJoinColumns = @JoinColumn(name = "author_id")
//    )
//    private Set<Author> authors;

//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(
//            name = "book_genres",
//            joinColumns = @JoinColumn(name = "book_id"),
//            inverseJoinColumns = @JoinColumn(name = "genre_id")
//    )
//    private Set<Gender> genres;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime updatedAt;

    @Column(nullable = false)
    private boolean deleted = false;

    private LocalDateTime deletedAt;
}