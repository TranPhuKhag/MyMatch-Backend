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
@SQLDelete(sql = "UPDATE review SET deleted = 1 WHERE id = ?")
@SQLRestriction("deleted = 0")
public class Review extends AbstractAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    Student student;

    @ManyToOne
    @JoinColumn(name = "lecturer_id", nullable = false)
    Lecturer lecturer;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    Course course;

    @Column(name = "overall_score")
    Double overallScore;

    @Column(nullable = false)
    Boolean verified = false;

    Boolean isAnonymous = false;

    @ManyToOne
    @JoinColumn(name = "semester_id", nullable = false)
    Semester semester;

    @Column(name = "evidence_url")
    String evidenceUrl;
}
