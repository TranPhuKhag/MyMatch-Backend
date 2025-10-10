package com.mymatch.dto.response.teammember;

import com.mymatch.dto.response.member.MemberResponse;
import com.mymatch.dto.response.skill.SkillResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Set;

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
