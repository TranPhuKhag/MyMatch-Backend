package com.mymatch.dto.response.team;

import com.mymatch.dto.response.campus.CampusResponse;
import com.mymatch.dto.response.course.CourseResponse;
import com.mymatch.dto.response.semester.SemesterResponse;
import com.mymatch.dto.response.student.StudentResponse;
import com.mymatch.dto.response.teammember.TeamMemberResponse;
import com.mymatch.dto.response.teamrequest.TeamRequestResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TeamResponse {
    Long id;
    String name;
    Integer memberMax;
    String description;
    String image;

    CourseResponse course;
    SemesterResponse semester;
    CampusResponse campus;
    StudentResponse createdBy;

    LocalDateTime createAt;
    List<TeamRequestResponse> teamRequest;
    List<TeamMemberResponse> teamMember;
    int requestCount;
    int memberCount;
}
