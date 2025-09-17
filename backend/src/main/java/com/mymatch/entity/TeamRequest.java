package com.mymatch.entity;

import com.mymatch.common.AbstractAuditingEntity;
import com.mymatch.enums.RequestStatus;
import com.mymatch.enums.Urgency;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@SQLDelete(sql = "UPDATE team_request SET deleted = 1 WHERE id = ?")
@SQLRestriction("deleted = 0")
public class TeamRequest extends AbstractAuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @ManyToOne
    @JoinColumn(name = "team_id", nullable = false)
    Team team;
    @Column(nullable = false)
    String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    Urgency urgency = Urgency.NORMAL;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    RequestStatus status = RequestStatus.OPEN;

    @OneToMany(mappedBy = "teamRequest", cascade = CascadeType.ALL, orphanRemoval = true)
    List<TeamRequestSkill> skills;

}
