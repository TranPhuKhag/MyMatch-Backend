package com.mymatch.entity;

import com.mymatch.common.AbstractAuditingEntity;
import com.mymatch.enums.TransactionSource;
import com.mymatch.enums.TransactionStatus;
import com.mymatch.enums.TransactionType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Transaction extends AbstractAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false, unique = true)
    String transactionCode;

    // Số tiền của giao dịch, luôn là số dương
    @Column(nullable = false)
    Long coin;  // Số coin của transaction đơn vị (coin)
    Double amount; // Số tiền của transaction đơn vị (VND)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    TransactionType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    TransactionStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    TransactionSource source;

    String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wallet_id", nullable = false)
    Wallet wallet;

    @PrePersist
    public void generateTransactionCode() {
        if (transactionCode == null || transactionCode.isEmpty()) {
            String timestamp = ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));

            int randomNumber = ThreadLocalRandom.current().nextInt(1000, 10000);

            this.transactionCode = timestamp + randomNumber;
        }
    }
}