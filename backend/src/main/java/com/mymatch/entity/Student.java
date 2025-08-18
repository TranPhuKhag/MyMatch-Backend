package com.mymatch.entity;

import com.mymatch.common.AbstractAuditingEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@SQLDelete(sql = "UPDATE student SET deleted = 1 WHERE id = ?")
@SQLRestriction("deleted = 0")
public class Student extends AbstractAuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "student_code")
    Long studentCode;

    @ManyToOne
    @JoinColumn(name = "campus_id")
    Campus campus;

    String skill;

    Double goals;

    @Column( columnDefinition = "TEXT")
    String description;

    @OneToOne(mappedBy = "student")
    User user;


}
