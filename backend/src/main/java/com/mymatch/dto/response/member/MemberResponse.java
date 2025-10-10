package com.mymatch.dto.response.member;

import com.mymatch.dto.response.skill.SkillResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MemberResponse {
    Long id;
    String name;
    String note;
    String image;
    Set<MemberSkillResponse> memberSkills;

}
