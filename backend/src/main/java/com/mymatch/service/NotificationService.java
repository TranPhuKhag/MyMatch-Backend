package com.mymatch.service;

import com.mymatch.dto.response.email.EmailResponse;
import com.mymatch.enums.EmailType;

import java.util.Map;

public interface NotificationService {
    EmailResponse send(EmailType type, String toName, String toEmail, Map<String, Object> model);
}
