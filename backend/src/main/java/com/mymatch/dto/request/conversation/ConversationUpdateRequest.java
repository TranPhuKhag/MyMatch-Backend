package com.mymatch.dto.request.conversation;

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
public class ConversationUpdateRequest {
    String type;

    @Size(min = 1)
    @NotNull
    List<String> participantIds;
}