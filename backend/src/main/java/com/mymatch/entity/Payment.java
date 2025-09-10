package com.mymatch.entity;

import com.mymatch.common.AbstractAuditingEntity;
import com.mymatch.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Payment extends AbstractAuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    String orderId = UUID.randomUUID().toString(); // Unique ref cho SePay

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    Student student;

    @Column(nullable = false)
    Double amountVnd;

    @Column(nullable = false)
    Double coinAmount; // amountVnd / 1000

    String virtualAccount;

    String qrUrl;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    PaymentStatus status = PaymentStatus.PENDING;

    @OneToOne
    @JoinColumn(name = "transaction_id")
    Transaction transaction;

}
