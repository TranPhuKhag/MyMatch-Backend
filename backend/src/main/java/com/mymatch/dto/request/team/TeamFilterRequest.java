package com.mymatch.dto.request.team;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TeamFilterRequest {
    Long semesterId;
    Long campusId;
    Long courseId;
    String keyword; // tìm theo name/description

    Integer page = 1;
    Integer size = 10;
    String sortBy; // default createAt
    String sortDirection = "DESC";
}
