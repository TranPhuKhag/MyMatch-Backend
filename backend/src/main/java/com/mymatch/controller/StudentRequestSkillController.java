package com.mymatch.controller;

import com.mymatch.dto.request.studentrequestskill.StudentRequestSkillBulkRequest;
import com.mymatch.dto.response.ApiResponse;
import com.mymatch.dto.response.studentrequestskill.StudentRequestSkillResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.mymatch.service.StudentRequestSkillService;
import lombok.AccessLevel;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/student-request-skills")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StudentRequestSkillController {
    StudentRequestSkillService studentRequestSkillService;

    @PutMapping("/{requestId}")
    public ApiResponse<List<StudentRequestSkillResponse>> replaceAll(@PathVariable Long requestId,
                                                                     @RequestBody StudentRequestSkillBulkRequest req) {
        return ApiResponse.<List<StudentRequestSkillResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Cập nhật kỹ năng thành công")
                .result(studentRequestSkillService.replace(requestId, req))
                .build();
    }
}
