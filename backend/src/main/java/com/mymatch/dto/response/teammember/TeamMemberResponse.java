package com.mymatch.dto.response.teammember;

import com.mymatch.dto.response.member.MemberResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TeamMemberResponse {
    Long id;
    MemberResponse member;
    LocalDateTime createAt;
}
