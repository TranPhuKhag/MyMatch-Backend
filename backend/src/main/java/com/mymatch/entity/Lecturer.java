package com.mymatch.entity;

import jakarta.persistence.*;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.mymatch.common.AbstractAuditingEntity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@SQLDelete(sql = "UPDATE lecturer SET deleted = 1 WHERE id = ?")
@SQLRestriction("deleted = 0")
public class Lecturer extends AbstractAuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String name;

    String code;

    @Column(columnDefinition = "TEXT")
    String bio;

    @OneToMany(mappedBy = "lecturer", fetch = FetchType.LAZY)
    List<Review> reviews;

    @ManyToOne
    @JoinColumn(name = "campus_id", nullable = false)
    Campus campus;

    @ManyToMany
    List<Tag> tags;

}
