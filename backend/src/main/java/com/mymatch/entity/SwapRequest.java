package com.mymatch.entity;

import com.mymatch.common.AbstractAuditingEntity;
import com.mymatch.enums.ClassesSlot;
import com.mymatch.enums.SwapRequestStatus;
import com.mymatch.enums.Visibility;
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
@Table(name = "swap_request")
@SQLDelete(sql = "UPDATE swap_request SET deleted = 1 WHERE id = ?")
@SQLRestriction("deleted = 0")
public class SwapRequest extends AbstractAuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "student_id", nullable = false)
    Student student;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "course_id", nullable = false)
    Course course;

    @Column(name = "from_class", length = 10, nullable = false)
    String fromClass;

    @Column(name = "target_class", length = 10, nullable = false)
    String targetClass;

    @Enumerated(EnumType.STRING)
    @Column(name = "slot_from", length = 20)
    ClassesSlot slotFrom;

    @Enumerated(EnumType.STRING)
    @Column(name = "slot_to", length = 20)
    ClassesSlot slotTo;

    @Column(length = 500)
    String reason;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    Visibility visibility = Visibility.PUBLIC;

    @Column(name = "expires_at")
    LocalDateTime expiresAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecturer_from_id")
    Lecturer lecturerFrom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecturer_to_id")
    Lecturer lecturerTo;

    @Enumerated(EnumType.STRING)
    SwapRequestStatus status = SwapRequestStatus.SENT;
}
