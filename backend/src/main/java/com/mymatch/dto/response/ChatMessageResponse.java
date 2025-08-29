package com.mymatch.dto.response;
import com.mymatch.dto.response.student.StudentResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChatMessageResponse {
    Long id;
    ConversationResponse conversationResponse;
    boolean me;
    String message;
    StudentResponse sender;
    Instant createdDate;
}
