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
@SQLDelete(sql = "UPDATE material_purchase SET deleted = 1 WHERE id = ?")
@SQLRestriction("deleted = 0")
public class MaterialPurchase extends AbstractAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "material_id", nullable = false)
    Material material;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id", nullable = false)
    User buyer;

    @Column(nullable = false)
    Double totalCoin;        // Tổng tiền

    @Column(nullable = false)
    Double platformFee;        // Phí platform

    @Column(nullable = false)
    Double ownerEarning;

    @Column(nullable = false)
    String transactionId;


}
