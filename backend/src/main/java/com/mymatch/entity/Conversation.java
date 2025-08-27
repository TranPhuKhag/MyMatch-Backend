package com.mymatch.entity;

import com.mymatch.common.AbstractAuditingEntity;
import com.mymatch.enums.ConversationType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@SQLDelete(sql = "UPDATE conversation SET deleted = 1 WHERE id = ?")
@SQLRestriction("deleted = 0")
public class Conversation extends AbstractAuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false, unique = true)
    ConversationType type; // DIRECT/GROUP

//    @Column(name="direct_key")
//    String directKey;        // unique cho DIRECT

    @Column(name="participants_hash", unique = true)
    String participantsHash; // hash của các participant, unique
    @ManyToMany
    @JoinTable(
            name = "conversation_participants",
            joinColumns = @JoinColumn(name = "conversation_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id"))
    List<Student> participants;

    String title;

    String avatarUrl;

    @ManyToOne
    @JoinColumn(name="created_by_student_id")
    Student createdBy;
}
