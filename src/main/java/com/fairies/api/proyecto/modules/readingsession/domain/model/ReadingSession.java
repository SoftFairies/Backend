package com.fairies.api.proyecto.modules.readingsession.domain.model;

import com.fairies.api.proyecto.modules.user.domain.model.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "reading_sessions")
@EntityListeners(AuditingEntityListener.class)
@SQLDelete(sql = "UPDATE readings_sessions SET deleted = true, deleted_at = NOW() WHERE sesion_id = ?")
@SQLRestriction("deleted = false")
public class ReadingSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sesion_id")
    private Long sesionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "libro_id", nullable = false)
    private Long libroId;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(name = "minutos_leidos", nullable = false)
    private Long minutosLeidos;

    @Column(name = "paginas_avanzadas", nullable = false)
    private Long paginasAvanzadas;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", insertable = false)
    private LocalDateTime updatedAt;

    @Column(nullable = false)
    private boolean deleted = false;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
}
