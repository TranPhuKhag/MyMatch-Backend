package com.mymatch.dto.response.swap;

import com.mymatch.dto.response.student.StudentResponse;
import com.mymatch.dto.response.swaprequest.SwapRequestResponse;
import com.mymatch.enums.SwapDecision;
import com.mymatch.enums.SwapStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SwapResponse {
    Long id;
    SwapRequestResponse requestFrom;
    SwapRequestResponse requestTo;
    StudentResponse studentFrom;
    StudentResponse studentTo;
    SwapStatus status;
    String reason;
    LocalDateTime approvedAt;
    LocalDateTime createAt;
    LocalDateTime updateAt;
    SwapDecision fromDecision;
    SwapDecision toDecision;
}
