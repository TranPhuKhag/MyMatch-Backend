package com.mymatch.dto.request.conversation;

import com.mymatch.enums.ConversationType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ConversationCreationRequest {
    ConversationType type;

    @Size(min = 1)
    @NotNull
    List<Long> participantIds;
}