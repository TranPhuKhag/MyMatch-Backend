package com.mymatch.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import com.mymatch.common.AbstractAuditingEntity;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(
        name = "course",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"code", "university_id"})
        }
)
@SQLDelete(sql = "UPDATE course SET deleted = 1 WHERE id = ?")
@SQLRestriction("deleted = 0")
public class Course extends AbstractAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String code;

    @Column(nullable = false)
    String name;

    @ManyToOne
    @JoinColumn(name = "university_id", nullable = false)
    University university;
}
