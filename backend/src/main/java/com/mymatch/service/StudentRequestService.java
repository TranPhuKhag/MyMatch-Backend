package com.mymatch.service;

import com.mymatch.dto.request.studentrequest.StudentRequestCreationRequest;
import com.mymatch.dto.request.studentrequest.StudentRequestFilterRequest;
import com.mymatch.dto.request.studentrequest.StudentRequestUpdateRequest;
import com.mymatch.dto.response.PageResponse;
import com.mymatch.dto.response.studentrequest.StudentRequestResponse;

public interface StudentRequestService {
    StudentRequestResponse getStudentRequestById(Long id);
    StudentRequestResponse createStudentRequest(StudentRequestCreationRequest req);
    void deleteStudentRequest(Long id);
    StudentRequestResponse updateStudentRequest(Long id, StudentRequestUpdateRequest req);
    PageResponse<StudentRequestResponse> getAllStudentRequests(StudentRequestFilterRequest filterRequest,
                                                               int page,
                                                               int size,
                                                               String sortBy,
                                                               String sortDirection);

}
