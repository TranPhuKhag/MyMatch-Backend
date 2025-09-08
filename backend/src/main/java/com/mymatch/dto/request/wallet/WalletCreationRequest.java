package com.mymatch.dto.request.wallet;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WalletCreationRequest {
    @NotNull(message = "Số dư không được để trống")
    BigDecimal balance;

    @NotNull(message = "Loại ví không được để trống")
    String type;

    Long userId;
}