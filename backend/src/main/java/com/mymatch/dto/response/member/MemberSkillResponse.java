package com.mymatch.dto.response.member;

import com.mymatch.dto.response.skill.SkillResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MemberSkillResponse {
    Long id;
    SkillResponse skill;
}
