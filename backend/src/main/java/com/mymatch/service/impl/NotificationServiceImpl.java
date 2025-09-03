package com.mymatch.service.impl;

import com.mymatch.dto.request.email.Recipient;
import com.mymatch.dto.request.email.SendEmailRequest;
import com.mymatch.dto.response.email.EmailResponse;
import com.mymatch.enums.EmailType;
import com.mymatch.service.EmailRenderer;
import com.mymatch.service.EmailService;
import com.mymatch.service.NotificationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationServiceImpl implements NotificationService {
    EmailService emailService;
    EmailRenderer emailRenderer;

    public EmailResponse send(EmailType type, String toName, String toEmail, Map<String, Object> model) {
        EmailRenderer.RenderedEmail rendered = emailRenderer.render(type, model);

        SendEmailRequest request = SendEmailRequest.builder()
                .to(Recipient.builder().name(toName).email(toEmail).build())
                .subject(rendered.subject())
                .htmlContent(rendered.htmlContent())
                .build();

        return emailService.sendEmail(request);
    }
}
