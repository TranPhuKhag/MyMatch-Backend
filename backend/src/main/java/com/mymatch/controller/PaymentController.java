package com.mymatch.controller;

import com.mymatch.dto.response.ApiResponse;
import com.mymatch.dto.response.payment.PaymentResponse;
import com.mymatch.service.PaymentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentController {
    PaymentService paymentService;
    @ResponseStatus(HttpStatus.CREATED)
    @GetMapping("/qr")
    public ApiResponse<PaymentResponse> createQrCodePayment() {
        var result = paymentService.createQrCodePayment();
        return ApiResponse.<PaymentResponse>builder()
                .result(result)
                .build();
    }
}
