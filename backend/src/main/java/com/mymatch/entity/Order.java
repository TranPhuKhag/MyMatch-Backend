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
@Table(name = "orders")
@SQLDelete(sql = "UPDATE orders SET deleted = 1 WHERE id = ?")
@SQLRestriction("deleted = 0")
public class Order extends AbstractAuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String orderCode;
    String status; // PENDING, COMPLETED, CANCELLED
    Double amount;
    String paymentMethod; // CREDIT_CARD, PAYPAL, BANK_TRANSFER

    @OneToOne
    @JoinColumn(name = "student_id", nullable = false)
    Student student;
}
