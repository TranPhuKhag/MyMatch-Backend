package com.mymatch.controller;

import com.mymatch.dto.request.payment.SepayWebhookDTO;
import com.mymatch.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/payments/internal")
//@FieldDefaults(level = AccessLevel.PRIVATE)
public class InternalPaymentController {
    private final PaymentService paymentService;

    @Value("${sepay.webhook.api-key}")
    String sepayWebhookApiKey;

    @PostMapping("/webhooks/sepay-payment")
    public ResponseEntity<Map<String, Object>> handleSepayPaymentHook(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @RequestBody SepayWebhookDTO sepayWebhookDTO
    ) {
        String expected = "Apikey " + sepayWebhookApiKey;  // Fix: Thêm "Apikey " theo docs
        if (authorization == null || !authorization.equals(expected)) {
            log.warn("Invalid auth: {}", authorization);
            return ResponseEntity.status(401).body(Map.of("success", false));
        }
        try {
            paymentService.handleSepayPaymentHook(sepayWebhookDTO);
        } catch (Exception e) {
            log.error("Webhook error: {}", e.getMessage(), e);
        }
        return ResponseEntity.ok(Map.of("success", true));
    }
}
