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
@SQLDelete(sql = "UPDATE campus SET deleted = 1 WHERE id = ?")
@SQLRestriction("deleted = 0")
public class Campus extends AbstractAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String name;

    String imgUrl;

    @ManyToOne
    @JoinColumn(name = "university_id", nullable = false)
    University university;

    @Column(nullable = false)
    String address;
}
