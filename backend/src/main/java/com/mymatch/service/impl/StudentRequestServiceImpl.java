package com.mymatch.service.impl;
import com.mymatch.dto.request.studentrequest.StudentRequestCreationRequest;
import com.mymatch.dto.request.studentrequest.StudentRequestFilterRequest;
import com.mymatch.dto.request.studentrequest.StudentRequestUpdateRequest;
import com.mymatch.dto.request.wallet.WalletRequest;
import com.mymatch.dto.response.PageResponse;
import com.mymatch.dto.response.studentrequest.StudentRequestResponse;
import com.mymatch.entity.*;
import com.mymatch.enums.RequestStatus;
import com.mymatch.enums.TransactionSource;
import com.mymatch.enums.TransactionType;
import com.mymatch.exception.AppException;
import com.mymatch.exception.ErrorCode;
import com.mymatch.mapper.StudentRequestMapper;
import com.mymatch.repository.*;
import com.mymatch.service.StudentRequestService;
import com.mymatch.service.WalletService;
import com.mymatch.specification.StudentRequestSpecification;
import com.mymatch.utils.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.*;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class StudentRequestServiceImpl implements StudentRequestService {

    CourseRepository courseRepository;
    SemesterRepository semesterRepository;
    CampusRepository campusRepository;
    StudentRequestMapper studentRequestMapper;
    StudentRequestRepository studentRequestRepository;
    UserRepository userRepository;
    SkillRepository skillRepository;
    WalletService walletService;

    private static final long STUDENT_REQUEST_FEE_COIN = 2L;

    @Transactional
    @Override
    public StudentRequestResponse createStudentRequest(StudentRequestCreationRequest req) {
        User currentUser = userRepository.findById(SecurityUtil.getCurrentUserId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        if (currentUser.getStudent() == null) {
            throw new AppException(ErrorCode.STUDENT_INFO_REQUIRED);
        }
        WalletRequest walletRequest = WalletRequest.builder()
                .userId(currentUser.getId())
                .type(TransactionType.OUT)
                .source(TransactionSource.SERVICE_PURCHASE)
                .coin(STUDENT_REQUEST_FEE_COIN)
                .description("Đăng yêu cầu tìm đồng đội của Student: " + currentUser.getUsername())
                .build();
        walletService.deductFromWallet(walletRequest);
        Student student = currentUser.getStudent();
        Course course = courseRepository.findById(req.getCourseId())
                .orElseThrow(() -> new AppException(ErrorCode.COURSE_NOT_FOUND));
        Semester semester = semesterRepository.findById(req.getSemesterId())
                .orElseThrow(() -> new AppException(ErrorCode.SEMESTER_NOT_FOUND));
        Campus campus = campusRepository.findById(req.getCampusId())
                .orElseThrow(() -> new AppException(ErrorCode.CAMPUS_NOT_FOUND));

        StudentRequest studentRequest = studentRequestMapper.toEntity(req);
        studentRequest.setStudent(student);
        studentRequest.setCourse(course);
        studentRequest.setSemester(semester);
        studentRequest.setCampus(campus);
        studentRequest.setStatus(RequestStatus.OPEN);
        if (req.getSkillIds() != null && !req.getSkillIds().isEmpty()) {
            var distinct = req.getSkillIds().stream().distinct().toList();
            var skills = skillRepository.findAllById(distinct);
            if (skills.size() != distinct.size()) throw new AppException(ErrorCode.INVALID_PARAMETER);
            for (Skill s : skills) {
                studentRequest.getSkills().add(
                        StudentRequestSkill.builder()
                                .request(studentRequest)
                                .skill(s)
                                .build()
                );
            }
        }
        studentRequest = studentRequestRepository.save(studentRequest);
        return studentRequestMapper.toResponse(studentRequest);

    }

    @Transactional
    @Override
    public StudentRequestResponse updateStudentRequest(Long id, StudentRequestUpdateRequest req) {
        User currentUser = userRepository.findById(SecurityUtil.getCurrentUserId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        Student student = currentUser.getStudent();
        StudentRequest studentRequest = studentRequestRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.STUDENT_REQUEST_NOT_FOUND));
        if (!studentRequest.getStudent().getId().equals(student.getId())
                && !SecurityUtil.hasAuthority("student_request:update")) {
            throw new AppException(ErrorCode.ACCESS_DENIED);
        }
        studentRequestMapper.updateStudentRequest(studentRequest, req);
        studentRequest.setCourse(courseRepository.findById(req.getCourseId())
                .orElseThrow(() -> new AppException(ErrorCode.COURSE_NOT_FOUND)));
        studentRequest.setSemester(semesterRepository.findById(req.getSemesterId())
                .orElseThrow(() -> new AppException(ErrorCode.SEMESTER_NOT_FOUND)));
        studentRequest.setCampus(campusRepository.findById(req.getCampusId())
                .orElseThrow(() -> new AppException(ErrorCode.CAMPUS_NOT_FOUND)));
        if (req.getSkillIds() != null) {
            // Chuẩn hoá set id mới
            var newIds = new HashSet<>(req.getSkillIds());
            // current ids
            var currentIds = studentRequest.getSkills().stream()
                    .map(srs -> srs.getSkill().getId())
                    .collect(Collectors.toSet());

            // remove những cái không còn
            studentRequest.getSkills().removeIf(srs -> !newIds.contains(srs.getSkill().getId()));
            // thêm những cái mới
            newIds.removeAll(currentIds); // còn lại là những id cần add
            if (!newIds.isEmpty()) {
                var skillsToAdd = skillRepository.findAllById(newIds);
                if (skillsToAdd.size() != newIds.size()) throw new AppException(ErrorCode.INVALID_PARAMETER);
                for (Skill s : skillsToAdd) {
                    studentRequest.getSkills().add(
                            StudentRequestSkill.builder()
                                    .request(studentRequest)
                                    .skill(s)
                                    .build()
                    );
                }
            }
        }

        studentRequest = studentRequestRepository.save(studentRequest);
        return studentRequestMapper.toResponse(studentRequest);
    }

    @Override
    public StudentRequestResponse getStudentRequestById(Long id) {
        StudentRequest sr = studentRequestRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.STUDENT_REQUEST_NOT_FOUND));
        return studentRequestMapper.toResponse(sr);
    }
    @Transactional
    @Override
    public void deleteStudentRequest(Long id) {
        User currentUser = userRepository.findById(SecurityUtil.getCurrentUserId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        if (currentUser.getStudent() == null)
            throw new AppException(ErrorCode.STUDENT_INFO_REQUIRED);

        StudentRequest sr = studentRequestRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ENTITY_NOT_FOUND));

        if (!sr.getStudent().getId().equals(currentUser.getStudent().getId())
                && !SecurityUtil.hasAuthority("student_request:delete")) {
            throw new AppException(ErrorCode.ACCESS_DENIED);
        }

        studentRequestRepository.delete(sr);
    }

    @Override
    public PageResponse<StudentRequestResponse> getAllStudentRequests(StudentRequestFilterRequest filterRequest,
                                                       int page,
                                                       int size,
                                                       String sortBy,
                                                       String sortDirection) {

        Sort.Direction direction = Sort.Direction.fromOptionalString(sortDirection)
                .orElse(Sort.Direction.DESC);
        Sort sort = Sort.by(direction, (sortBy != null && !sortBy.isBlank()) ? sortBy : "createAt");

        Pageable pageable = PageRequest.of(Math.max(page - 1, 0), Math.max(size, 1), sort);

        var spec = StudentRequestSpecification.byFilter(filterRequest);
        Page<StudentRequest> pages = studentRequestRepository.findAll(spec, pageable);

        var data = pages.getContent().stream()
                .map(studentRequestMapper::toResponse)
                .toList();

        return PageResponse.<StudentRequestResponse>builder()
                .data(data)
                .pageSize(pages.getSize())
                .totalPages(pages.getTotalPages())
                .totalElements(pages.getTotalElements())
                .currentPage(page)
                .build();
    }
}
