package com.mymatch.service;

import com.mymatch.dto.request.payment.SepayWebhookDTO;
import com.mymatch.dto.response.payment.PaymentResponse;

public interface PaymentService {
    PaymentResponse createQrCodePayment();
    void handleSepayPaymentHook(SepayWebhookDTO sepayWebhookDTO);
}
