package com.mymatch.dto.request.student;

import com.mymatch.entity.Campus;
import com.mymatch.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StudentCreationRequest {

    String studentCode;
    Long campusId;

    String skill;
    Double goals;
    String description;
    String major;
}
