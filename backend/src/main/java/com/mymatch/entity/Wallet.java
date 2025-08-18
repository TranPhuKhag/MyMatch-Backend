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
@SQLDelete(sql = "UPDATE wallet SET deleted = 1 WHERE id = ?")
@SQLRestriction("deleted = 0")
public class Wallet extends AbstractAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    Long coin;

    @OneToOne(mappedBy = "wallet")
    User user;
}
