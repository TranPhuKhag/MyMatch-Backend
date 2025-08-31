package com.mymatch.service.impl;

import com.mymatch.dto.request.email.EmailRequest;
import com.mymatch.dto.request.email.Recipient;
import com.mymatch.dto.request.email.SendEmailRequest;
import com.mymatch.dto.request.email.Sender;
import com.mymatch.dto.response.email.EmailResponse;
import com.mymatch.exception.AppException;
import com.mymatch.exception.ErrorCode;
import com.mymatch.repository.httpClient.EmailClient;
import com.mymatch.service.EmailService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmailServiceImpl implements EmailService {
    EmailClient emailClient;

    @Value("${notification.email.brevo-apikey}")
    @NonFinal
    String apiKey;
    @Override
    public EmailResponse sendEmail(SendEmailRequest request) {
        EmailRequest emailRequest = EmailRequest.builder()
                .sender(Sender.builder()
                        .name("MyMatch")
                        .email("trankhang0990@gmail.com")
                        .build())
                .to(List.of(Recipient.builder()
                        .email(request.getTo().getEmail())
                        .name(request.getTo().getName())
                        .build()))
                .subject(request.getSubject())
                .htmlContent(request.getHtmlContent())
                .build();
        try {
            return emailClient.sendEmail(apiKey, emailRequest);
        } catch (Exception e) {
            log.error("Failed to send email to {}: {}", request.getTo().getEmail(), e.getMessage());
            throw new AppException(ErrorCode.CANNOT_SEND_EMAIL);
        }
    }
}
