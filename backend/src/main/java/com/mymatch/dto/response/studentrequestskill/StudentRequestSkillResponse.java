package com.mymatch.dto.response.studentrequestskill;

import com.mymatch.dto.response.skill.SkillResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class StudentRequestSkillResponse {
    Long id;
    Long requestId;
    SkillResponse skill;
}
