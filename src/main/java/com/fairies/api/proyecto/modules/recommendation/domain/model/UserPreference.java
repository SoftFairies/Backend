package com.fairies.api.proyecto.modules.recommendation.domain.model;

import com.fairies.api.proyecto.modules.format.domain.model.Format;
import com.fairies.api.proyecto.modules.gender.domain.model.Gender;
import com.fairies.api.proyecto.modules.user.domain.model.User;
import jakarta.persistence.*;
import lombok.*;
import java.util.Set;
import java.util.UUID;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(name = "recommendations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPreference {
    @Id
    @GeneratedValue(generator = "uuid-v7")
    @UuidGenerator(style = UuidGenerator.Style.VERSION_7)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_pref_formats")
    private Set<Format> formats;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_pref_genres")
    private Set<Gender> genres;


}