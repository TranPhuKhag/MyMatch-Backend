package com.mymatch.service;

import com.mymatch.dto.request.studentrequestskill.StudentRequestSkillBulkRequest;
import com.mymatch.dto.response.studentrequestskill.StudentRequestSkillResponse;

import java.util.List;

public interface StudentRequestSkillService {
    // Thay thế toàn bộ bộ skill bằng danh sách mới (clear & rebuild)
    List<StudentRequestSkillResponse> replace(Long requestId, StudentRequestSkillBulkRequest req);

}
