package com.mymatch.service;

import com.mymatch.dto.request.email.SendEmailRequest;
import com.mymatch.dto.response.email.EmailResponse;

public interface EmailService {
    EmailResponse sendEmail(SendEmailRequest request);
}
