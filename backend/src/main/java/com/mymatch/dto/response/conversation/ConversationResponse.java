package com.mymatch.dto.response.conversation;

import com.mymatch.dto.response.student.StudentResponse;
import com.mymatch.dto.response.swap.SwapResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ConversationResponse {
    Long id;
    String type; // GROUP, DIRECT
    String participantsHash;
    String conversationAvatar;
    String conversationName;
    List<StudentResponse> participants;
    boolean me;
    SwapResponse swap;
}
