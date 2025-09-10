package com.mymatch.dto.response.chatmessage;
import com.mymatch.dto.response.conversation.ConversationResponse;
import com.mymatch.dto.response.student.StudentResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChatMessageResponse {
    Long id;
    ConversationResponse conversation;
    boolean me;
    String message;
    StudentResponse sender;
    LocalDateTime createAt;
    LocalDateTime updateAt;
}
