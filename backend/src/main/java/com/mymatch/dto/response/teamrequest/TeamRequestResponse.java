package com.mymatch.dto.response.teamrequest;


import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TeamRequestResponse {
    Long id;
    String title;
    Set<TeamRequestSkillResponse> skills;
}
