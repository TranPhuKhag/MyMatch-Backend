package com.mymatch.service;

import com.mymatch.enums.EmailType;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.Locale;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmailRenderer {
    SpringTemplateEngine templateEngine;
    public RenderedEmail render(EmailType type, Map<String, Object> model) {
        String template = type.name();
        Context ctx = new Context(Locale.ENGLISH, model);
        try {
            String subject = templateEngine
                    .process(template, java.util.Set.of("#subject"), ctx)
                    .trim();

            if (subject.isBlank()) {
                log.warn("Subject fragment missing/empty in template {}", template);
                subject = "MyMatch notification";
            }
            String html = templateEngine.process(template, ctx);

            return new RenderedEmail(subject, html);
        } catch (Exception e) {
            log.error("Failed to render template {} for English", template, e);
            throw new RuntimeException("Template render error");
        }
    }

    public record RenderedEmail(String subject, String htmlContent) {}
}