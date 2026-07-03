package com.fairies.api.proyecto.modules.book.infrastructure.persistence;

import com.fairies.api.proyecto.modules.book.domain.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {
    Optional<Book> findByIsbn(String isbn);

    @Query("SELECT DISTINCT b FROM Book b LEFT JOIN b.authors a WHERE " +
            "LOWER(b.title) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(b.isbn) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(a.name) LIKE LOWER(CONCAT('%', :query, '%'))")
    Page<Book> searchBooks(@Param("query") String query, Pageable pageable);
}