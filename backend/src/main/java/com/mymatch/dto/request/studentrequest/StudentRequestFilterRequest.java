package com.mymatch.dto.request.studentrequest;

import com.mymatch.enums.RequestStatus;
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
public class StudentRequestFilterRequest {
    Long studentId;                  // nếu không có quyền đọc tất cả, sẽ tự set = currentStudentId
    Long courseId;
    Long semesterId;
    Long campusId;
    RequestStatus status;            // OPEN/CLOSED
    String classCode;
}
