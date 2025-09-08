package com.mymatch.dto.request.wallet;


import com.mymatch.enums.TransactionSource;
import com.mymatch.enums.TransactionType;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WalletRequest {
    Long userId;
    Long coin;
    TransactionSource source;
    TransactionType type;
    String description;
}
