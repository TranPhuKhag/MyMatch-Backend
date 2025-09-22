package com.mymatch.dto.response.teamrequest;

import com.mymatch.dto.response.skill.SkillResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TeamRequestSkillResponse {
    Long id;
    SkillResponse skill;
}
