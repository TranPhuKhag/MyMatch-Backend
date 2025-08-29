package com.mymatch.service.impl;


import com.mymatch.dto.request.swap.*;
import com.mymatch.dto.response.PageResponse;
import com.mymatch.dto.response.swap.SwapResponse;
import com.mymatch.entity.*;
import com.mymatch.enums.SwapDecision;
import com.mymatch.enums.SwapMode;
import com.mymatch.enums.SwapStatus;
import com.mymatch.exception.AppException;
import com.mymatch.exception.ErrorCode;
import com.mymatch.mapper.StudentMapper;
import com.mymatch.mapper.SwapMapper;
import com.mymatch.mapper.SwapRequestMapper;
import com.mymatch.repository.*;
import com.mymatch.service.SwapService;
import com.mymatch.specification.SwapSpecification;
import com.mymatch.utils.SecurityUtil;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.AccessLevel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import java.util.Objects;

import static com.mymatch.utils.SecurityUtil.hasAuthority;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SwapServiceImpl implements SwapService {
    SwapRepository swapRepository;
    SwapRequestRepository swapRequestRepository;
    UserRepository userRepository;
    SwapMapper swapMapper;
    SwapRequestMapper swapRequestMapper;
    StudentMapper studentMapper;
    @Override
    public void createSwap(SwapRequest swapRequestCurrent , SwapRequest existingSwapRequest) {
        log.info("Creating swap between SwapRequest ID {} and SwapRequest ID {}",
                swapRequestCurrent.getId(), existingSwapRequest.getId());
        Swap swap = Swap.builder()
                .requestFrom(existingSwapRequest)
                .requestTo(swapRequestCurrent)
                .studentFrom(existingSwapRequest.getStudent())
                .studentTo(swapRequestCurrent.getStudent())
                .fromDecision(SwapDecision.PENDING)
                .toDecision(SwapDecision.PENDING)
                .status(SwapStatus.PENDING)
                .mode(SwapMode.AUTOMATIC)
                .build();
        if (swapRepository.existsByPairEitherOrder(swapRequestCurrent.getId(), existingSwapRequest.getId())) {
            throw new AppException(ErrorCode.SWAP_ALREADY_EXISTS);
        }
            swapRepository.save(swap);
    }

    @Transactional(Transactional.TxType.SUPPORTS)
    @Override
    public PageResponse<SwapResponse> getAll(SwapFilterRequest req) {
        Specification<Swap> spec = SwapSpecification.withFilter(req);
        Long currentUserService = userRepository.findById(SecurityUtil.getCurrentUserId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND))
                .getStudent()
                .getId();
        final Long viewerId = hasAuthority("swap:read") ? null : currentUserService;

        if (viewerId != null) {
            spec = spec.and((root, q, cb) -> cb.or(
                    cb.equal(root.get("studentFrom").get("id"), viewerId),
                    cb.equal(root.get("studentTo").get("id"), viewerId)
            ));
        }

        String sortBy = (req.getSortBy() == null || req.getSortBy().isBlank()) ? "id" : req.getSortBy();
        Sort.Direction dir = "desc".equalsIgnoreCase(req.getSortDirection()) ? Sort.Direction.DESC : Sort.Direction.ASC;

        int page = Math.max(req.getPage() - 1, 0);
        int size = Math.min(Math.max(req.getSize(), 1), 200);

        Pageable pageable = PageRequest.of(page, size, Sort.by(new Sort.Order(dir, sortBy)));
        Page<Swap> pages = swapRepository.findAll(spec, pageable);

        var data = pages.getContent().stream()
                .map(s -> viewerId == null ? swapMapper.toResponse(s) : toViewerResponse(s, viewerId))
                .toList();
        return PageResponse.<SwapResponse>builder()
                .data(data)
                .pageSize(pages.getSize())
                .totalPages(pages.getTotalPages())
                .totalElements(pages.getTotalElements())
                .currentPage(req.getPage())
                .build();
    }
    public SwapResponse getById(Long id) {
        Swap swap = new Swap();
        if (!hasAuthority("swap:read")) {
            Long studentId = userRepository.findById(SecurityUtil.getCurrentUserId())
                    .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND))
                    .getStudent()
                    .getId();

            swap = swapRepository.findByIdAndStudentFromIdOrStudentToId(id, studentId, studentId)
                    .orElseThrow(() -> new AppException(ErrorCode.SWAP_NOT_FOUND));
           return toViewerResponse(swap, studentId);
        }
        else{
            swap = swapRepository.findById(id)
                    .orElseThrow(() -> new AppException(ErrorCode.SWAP_NOT_FOUND));
        }
        return swapMapper.toResponse(swap);
    }

    private SwapResponse toViewerResponse(Swap s, Long viewerId) {
        boolean iAmFrom = Objects.equals(viewerId, s.getStudentFrom().getId());

        // Chọn đúng “from/to” theo NGƯỜI ĐANG XEM
        var reqFrom = iAmFrom ? s.getRequestFrom() : s.getRequestTo();
        var reqTo   = iAmFrom ? s.getRequestTo()   : s.getRequestFrom();
        var stuFrom = iAmFrom ? s.getStudentFrom() : s.getStudentTo();
        var stuTo   = iAmFrom ? s.getStudentTo()   : s.getStudentFrom();

        // Map entity -> DTO bằng mapper
        var reqFromDto = (reqFrom != null) ? swapRequestMapper.toResponse(reqFrom) : null;
        var reqToDto   = (reqTo   != null) ? swapRequestMapper.toResponse(reqTo)   : null;
        var stuFromDto = (stuFrom != null) ? studentMapper.toResponse(stuFrom)     : null;
        var stuToDto   = (stuTo   != null) ? studentMapper.toResponse(stuTo)       : null;

        return SwapResponse.builder()
                .id(s.getId())
                .requestFrom(reqFromDto)
                .requestTo(reqToDto)
                .studentFrom(stuFromDto)
                .studentTo(stuToDto)
                .status(s.getStatus())
                .reason(s.getReason())
                .createAt(s.getCreateAt())
                .updateAt(s.getUpdateAt())
                // “Quyết định của tôi” phải hiện ở fromDecision trong góc nhìn của tôi
                .fromDecision(iAmFrom ? s.getFromDecision() : s.getToDecision())
                .toDecision(iAmFrom ? s.getToDecision()   : s.getFromDecision())
                .build();
    }

}
