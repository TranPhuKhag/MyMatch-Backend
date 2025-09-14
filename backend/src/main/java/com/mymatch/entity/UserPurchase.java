package com.mymatch.entity;

import com.mymatch.common.AbstractAuditingEntity;
import com.mymatch.enums.PurchaseStatus;
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
@Table(name = "user_purchases")
@SQLDelete(sql = "UPDATE user_purchase SET deleted = 1 WHERE id = ?")
@SQLRestriction("deleted = 0")
public class UserPurchase extends AbstractAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "plan_id", nullable = false)
    Plan plan;

    @Column(nullable = false)
    LocalDateTime purchaseDate = LocalDateTime.now();

    @Column(nullable = false)
    LocalDateTime expiryDate;

    @Column(nullable = false)
    Long costCoin;                 // snapshot giá tại thời điểm mua (đồng bộ với Plan.coin hiện tại)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    PurchaseStatus status;
}
