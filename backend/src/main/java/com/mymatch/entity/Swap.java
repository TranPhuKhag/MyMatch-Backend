package com.mymatch.entity;

import com.mymatch.common.AbstractAuditingEntity;
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
@Table(name = "swap")
@SQLDelete(sql = "UPDATE swap SET deleted = 1 WHERE id = ?")
@SQLRestriction("deleted = 0")
public class Swap extends AbstractAuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    // 2 yêu cầu ghép cặp
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "request_from_id", nullable = false)
    SwapRequest requestFrom;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "request_to_id", nullable = false)
    SwapRequest requestTo;

    // 2 sinh viên
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "student_from_id", nullable = false)
    Student studentFrom;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "student_to_id", nullable = false)
    Student studentTo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    SwapStatus status = SwapStatus.PENDING;

    @Column(length = 500)
    String reason;

    @Column(name = "approved_at")
    LocalDateTime approvedAt;
}
