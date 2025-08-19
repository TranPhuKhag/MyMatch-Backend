package com.mymatch.dto.response.wallet;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WalletResponse {
    Long id;
    Long coin;
    LocalDateTime createAt;
    LocalDateTime updateAt;
}
