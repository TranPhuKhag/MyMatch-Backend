package com.mymatch.entity;

import com.mymatch.common.AbstractAuditingEntity;
import com.mymatch.enums.SwapDecision;
import com.mymatch.enums.SwapMode;
import com.mymatch.enums.SwapStatus;
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
@Table(
        name = "swap",
        indexes = {
                @Index(name = "idx_swap_request_from", columnList = "request_from_id"),
                @Index(name = "idx_swap_request_to", columnList = "request_to_id"),
                @Index(name = "idx_swap_student_from", columnList = "student_from_id"),
                @Index(name = "idx_swap_student_to", columnList = "student_to_id")
        }
)
@SQLDelete(sql = "UPDATE swap SET deleted = 1 WHERE id = ?")
@SQLRestriction("deleted = 0")
public class Swap extends AbstractAuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "request_from_id", nullable = false)
    SwapRequest requestFrom;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "request_to_id", nullable = false)
    SwapRequest requestTo;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "student_from_id", nullable = false)
    Student studentFrom;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "student_to_id", nullable = false)
    Student studentTo;

    @Enumerated(EnumType.STRING)
    @Column(name = "to_decision", nullable = false, length = 20)
    @Builder.Default
    SwapDecision toDecision = SwapDecision.PENDING;

    @Enumerated(EnumType.STRING)
    @Column(name = "from_decision", nullable = false, length = 20)
    @Builder.Default
    SwapDecision fromDecision = SwapDecision.PENDING;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    SwapStatus status = SwapStatus.PENDING;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    SwapMode mode = SwapMode.MANUAL;

    @Column(length = 500)
    String reason;

    @Column(name = "matched_at")
    LocalDateTime matchedAt;
}
