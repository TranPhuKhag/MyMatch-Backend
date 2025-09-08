package com.mymatch.dto.response.transaction;

import com.mymatch.entity.Wallet;
import com.mymatch.enums.TransactionSource;
import com.mymatch.enums.TransactionStatus;
import com.mymatch.enums.TransactionType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransactionResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String transactionCode;
    Long coin;  // Số coin của transaction đơn vị (coin)
    Double amount; // Số tiền của transaction đơn vị (VND)
    TransactionType type;
    TransactionStatus status;
    TransactionSource source;
    String description;
}
