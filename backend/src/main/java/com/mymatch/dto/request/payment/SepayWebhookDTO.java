package com.mymatch.dto.request.payment;


import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SepayWebhookDTO{
        Long id;
        String gateway;
        String transactionDate;   // "yyyy-MM-dd HH:mm:ss"
        String accountNumber;
        String code;              // sẽ có nếu bạn bật “Nhận diện mã thanh toán” + cấu hình pattern
        String content;
        String transferType;      // "in" | "out"
        BigDecimal transferAmount;
        BigDecimal accumulated;
        String subAccount;        // dùng nếu sau này chuyển sang VA theo đơn
        String referenceCode;
        String description;
}

