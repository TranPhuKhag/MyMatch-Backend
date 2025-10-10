package com.mymatch.dto.request.studentrequestskill;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class StudentRequestSkillBulkRequest {
    @NotEmpty(message = "Danh sách skill không được rỗng")
    Set<Long> skillIds;
}
