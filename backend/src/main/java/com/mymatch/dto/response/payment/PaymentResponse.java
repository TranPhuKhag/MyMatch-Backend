package com.mymatch.dto.response.payment;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentResponse {
    String accountNumber;
    String accountName;
    String bankCode;
//    String amount;
    String content;
    String qrUrl;
}
