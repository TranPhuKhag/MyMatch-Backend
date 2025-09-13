package com.mymatch.entity;

import com.mymatch.common.AbstractAuditingEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@SQLDelete(sql = "UPDATE plan SET deleted = 1 WHERE id = ?")
@SQLRestriction("deleted = 0")
public class Plan extends AbstractAuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false, length = 255)
    String name;

    @Column(columnDefinition = "TEXT")
    String description;

    Double coin; // coin

    @Builder.Default
    @Column(nullable = false)
    int purchaseCount = 0;

    String imageUrl;

    @Column(nullable = false)
    int durationDays;            // Số ngày hiệu lực
}
