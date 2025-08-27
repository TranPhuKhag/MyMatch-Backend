package com.mymatch.dto.response;

import com.mymatch.entity.ParticipantInfo;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChatMessageResponse {
    String id;
    String conversationId;
    boolean me; // nếu ChatMessage này của người dùng hiện tại gửi thì true, ngược lại false
    // Display thứ tự của mình nằm trên tay trái, người khác nằm bên phải
    // nội suy ra
    String message;
    ParticipantInfo sender;
    Instant createdDate;
}
