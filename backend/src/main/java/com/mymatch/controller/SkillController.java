package com.mymatch.controller;

import com.mymatch.dto.response.ApiResponse;
import com.mymatch.dto.response.skill.SkillResponse;
import com.mymatch.service.SkillService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/skills")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SkillController {
    SkillService skillService;
    @GetMapping
    public ApiResponse<List<SkillResponse>> getAllSkills() {
        return ApiResponse.<List<SkillResponse>>builder()
                .message("Lấy danh sách kỹ năng thành công")
                .code(HttpStatus.OK.value())
                .result(skillService.getAllSkills())
                .build();
    }
}
