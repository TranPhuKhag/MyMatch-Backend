    package com.mymatch.service.impl;

    import com.mymatch.dto.request.swaprequest.SwapRequestCreationRequest;
    import com.mymatch.dto.request.swaprequest.SwapRequestFilterRequest;
    import com.mymatch.dto.request.swaprequest.SwapRequestUpdateRequest;
    import com.mymatch.dto.response.PageResponse;
    import com.mymatch.dto.response.swaprequest.SwapRequestResponse;
    import com.mymatch.entity.*;
    import com.mymatch.enums.SwapRequestStatus;
    import com.mymatch.exception.AppException;
    import com.mymatch.exception.ErrorCode;
    import com.mymatch.mapper.SwapRequestMapper;
    import com.mymatch.repository.*;
    import com.mymatch.service.SwapRequestService;
    import com.mymatch.service.SwapService;
    import com.mymatch.specification.SwapRequestSpecification;
    import jakarta.transaction.Transactional;
    import lombok.AccessLevel;
    import lombok.RequiredArgsConstructor;
    import lombok.experimental.FieldDefaults;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.data.domain.Page;
    import org.springframework.data.domain.PageRequest;
    import org.springframework.data.domain.Pageable;
    import org.springframework.data.domain.Sort;
    import org.springframework.security.core.context.SecurityContextHolder;
    import org.springframework.stereotype.Service;

    import java.util.List;
    import java.util.stream.Collectors;

    import static com.mymatch.utils.SecurityUtil.hasAuthority;

    @Slf4j
    @Service
    @RequiredArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
    public class SwapRequestServiceImpl implements SwapRequestService {
        UserRepository userRepository;
        SwapRequestMapper swapRequestMapper;
        StudentRepository studentRepository;
        CourseRepository courseRepository;
        SwapRequestRepository swapRequestRepository;
        LecturerRepository lecturerRepository;
        SwapService swapService;
        @Override
        @Transactional
        public SwapRequestResponse createSwapRequest(SwapRequestCreationRequest request) {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
            Student student = studentRepository.findById(user.getStudent().getId()).orElseThrow(()
                    -> new AppException(ErrorCode.STUDENT_NOT_FOUND));
            Course course = courseRepository.findByCode(request.getCodeCourse()).orElseThrow(()
                    -> new AppException(ErrorCode.COURSE_NOT_FOUND));
            Lecturer lecturerFrom = lecturerRepository.findByCode(request.getCodeLecturerFrom()).orElseThrow(()
                    -> new AppException(ErrorCode.LECTURER_NOT_FOUND));
            Lecturer lecturerTo = lecturerRepository.findByCode(request.getCodeLecturerTo()).orElseThrow(()
                    -> new AppException(ErrorCode.LECTURER_NOT_FOUND));
            SwapRequest existing = swapRequestRepository.findExistingRequestByStudent(
                    course.getId(),
                    request.getFromClass(),
                    request.getTargetClass(),
                    lecturerFrom.getId(),
                    lecturerTo.getId(),
                    request.getSlotFrom(),
                    request.getSlotTo(),
                    student.getId()
            ).orElse(null);
            if (existing != null) {
                throw new AppException(ErrorCode.SWAPREQUEST_ALREADY_EXISTS);
            }
            SwapRequest partners = swapRequestRepository.findMatchingPartnerRequests(
                    course.getId(),
                    request.getTargetClass(),
                    request.getFromClass(),
                    lecturerTo.getId(),
                    lecturerFrom.getId(),
                    request.getSlotTo(),
                    request.getSlotFrom(),
                    student.getId()
            ).orElse(null);

            SwapRequest swapRequest = swapRequestMapper.toEntity(request);
            swapRequest.setStudent(student);
            swapRequest.setCourse(course);
            swapRequest.setLecturerFrom(lecturerFrom);
            swapRequest.setLecturerTo(lecturerTo);
            SwapRequest curentSwapRequest =  swapRequestRepository.save(swapRequest);
            if (partners != null) {
                swapService.createSwap(curentSwapRequest, partners);
                return swapRequestMapper.toResponse(curentSwapRequest);
            } else {
                return swapRequestMapper.toResponse(curentSwapRequest);
            }
        }

        @Override
        @Transactional
        public SwapRequestResponse updateSwapRequest(Long id, SwapRequestUpdateRequest request) {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
            Student student = studentRepository.findById(user.getStudent().getId()).orElseThrow(()
                    -> new AppException(ErrorCode.STUDENT_NOT_FOUND));
            Course course = courseRepository.findByCode(request.getCodeCourse()).orElseThrow(()
                    -> new AppException(ErrorCode.COURSE_NOT_FOUND));
            Lecturer lecturerFrom = lecturerRepository.findByCode(request.getCodeLecturerFrom()).orElseThrow(()
                    -> new AppException(ErrorCode.LECTURER_NOT_FOUND));
            Lecturer lecturerTo = lecturerRepository.findByCode(request.getCodeLecturerTo()).orElseThrow(()
                    -> new AppException(ErrorCode.LECTURER_NOT_FOUND));
            SwapRequest swapRequest = swapRequestRepository.findById(id)
                    .orElseThrow(() -> new AppException(ErrorCode.ENTITY_NOT_FOUND));
            swapRequestMapper.update(swapRequest, request);
            swapRequest.setCourse(course);
            swapRequest.setStudent(student);
            swapRequest.setLecturerFrom(lecturerFrom);
            swapRequest.setLecturerTo(lecturerTo);
            swapRequest = swapRequestRepository.save(swapRequest);
            return swapRequestMapper.toResponse(swapRequest);
        }

        @Override
        public SwapRequestResponse getSwapRequestById(Long id) {
            SwapRequest swapRequest = new SwapRequest();
            if (!hasAuthority("swaprequest:read")) {
                Long studentId = CurrentUserService();

                swapRequest = swapRequestRepository.findByIdAndStudentId(id, studentId)
                        .orElseThrow(() -> new AppException(ErrorCode.SWAPREQUEST_NOT_FOUND));
            }
            else {
                swapRequest = swapRequestRepository.findById(id)
                        .orElseThrow(() -> new AppException(ErrorCode.SWAPREQUEST_NOT_FOUND));
            }
            return swapRequestMapper.toResponse(swapRequest);
        }

        @Override
        public void deleteSwapRequest(Long id) {
            SwapRequest swapRequest = swapRequestRepository.findById(id)
                    .orElseThrow(() -> new AppException(ErrorCode.ENTITY_NOT_FOUND));
            swapRequestRepository.delete(swapRequest);
        }

        @Override
        public PageResponse<SwapRequestResponse> getAllSwapRequests(SwapRequestFilterRequest req) {
            var spec = SwapRequestSpecification.withFilter(req);

            if (!hasAuthority("swaprequest:read")) {
                Long studentId = CurrentUserService();
                spec = spec.and((root, query, cb) -> cb.equal(root.get("student").get("id"), studentId));
            }

            var sortBy = (req.getSortBy() == null || req.getSortBy().isBlank()) ? "id" : req.getSortBy();
            Pageable pageable = PageRequest.of(
                    Math.max(req.getPage() - 1, 0),
                    req.getSize(),
                    Sort.by(sortBy)
            );

            Page<SwapRequest> pages = swapRequestRepository.findAll(spec, pageable);
            List<SwapRequestResponse> data = pages.getContent().stream()
                    .map(swapRequestMapper::toResponse)
                    .collect(Collectors.toList());

            return PageResponse.<SwapRequestResponse>builder()
                    .data(data)
                    .pageSize(pages.getSize())
                    .totalPages(pages.getTotalPages())
                    .totalElements(pages.getTotalElements())
                    .currentPage(req.getPage())
                    .build();
        }

        @Override
        public SwapRequestResponse cancelSwapRequest(Long id) {
            SwapRequest swapRequest = new SwapRequest();
            if (!hasAuthority("swaprequest:cancel")) {
                Long studentId = CurrentUserService();
                swapRequest = swapRequestRepository.findByIdAndStudentId(id, studentId)
                        .orElseThrow(() -> new AppException(ErrorCode.SWAPREQUEST_NOT_FOUND));
            }
            else{
                swapRequest = swapRequestRepository.findById(id)
                        .orElseThrow(() -> new AppException(ErrorCode.SWAPREQUEST_NOT_FOUND));
            }

            swapRequest.setStatus(SwapRequestStatus.CANCELLED);
            swapRequest = swapRequestRepository.save(swapRequest);
            return swapRequestMapper.toResponse(swapRequest);
        }
        private Long CurrentUserService () {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
            if (user.getStudent() == null) throw new AppException(ErrorCode.STUDENT_NOT_FOUND);
            return user.getStudent().getId();
        }
    }
