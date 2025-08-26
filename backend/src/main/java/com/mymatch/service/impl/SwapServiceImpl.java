package com.mymatch.service.impl;


import com.mymatch.dto.request.swap.*;
import com.mymatch.dto.response.PageResponse;
import com.mymatch.dto.response.swap.SwapResponse;
import com.mymatch.entity.*;
import com.mymatch.enums.SwapDecision;
import com.mymatch.enums.SwapStatus;
import com.mymatch.exception.AppException;
import com.mymatch.exception.ErrorCode;
import com.mymatch.mapper.SwapMapper;
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
                .build();
        swapRepository.save(swap);
    }

    @Transactional(Transactional.TxType.SUPPORTS)
    @Override
    public PageResponse<SwapResponse> getAll(SwapFilterRequest req) {
        Specification<Swap> spec = SwapSpecification.withFilter(req);

//        if (!hasAuthority("swap:read")) {
//            Long studentId = currentUserService();
//            spec = spec.and((root, q, cb) -> cb.or(
//                    cb.equal(root.get("studentFrom").get("id"), studentId),
//                    cb.equal(root.get("studentTo").get("id"), studentId)
//            ));
//        }
        final Long viewerId = hasAuthority("swap:read") ? null : currentUserService();

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

//        var data = pages.getContent().stream().map(swapMapper::toResponse).toList();
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
            Long studentId = currentUserService();
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

    private Long currentUserService () {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        if (user.getStudent() == null) throw new AppException(ErrorCode.STUDENT_NOT_FOUND);
        return user.getStudent().getId();
    }
    private SwapResponse toViewerResponse(Swap s, Long viewerId) {
        boolean iAmFrom = Objects.equals(viewerId, s.getStudentFrom().getId());

        Long requestFromId = (iAmFrom ? s.getRequestFrom().getId() : s.getRequestTo().getId());
        Long requestToId   = (iAmFrom ? s.getRequestTo().getId()   : s.getRequestFrom().getId());

        Long studentFromId = (iAmFrom ? s.getStudentFrom().getId() : s.getStudentTo().getId());
        Long studentToId   = (iAmFrom ? s.getStudentTo().getId()   : s.getStudentFrom().getId());

        // Quyết định “của tôi” phải hiển thị ở fromDecision trong góc nhìn của tôi
        var fromDecision = (iAmFrom ? s.getFromDecision() : s.getToDecision());
        var toDecision   = (iAmFrom ? s.getToDecision()   : s.getFromDecision());

        return SwapResponse.builder()
                .id(s.getId())
                .requestFromId(requestFromId)
                .requestToId(requestToId)
                .studentFromId(studentFromId)
                .studentToId(studentToId)
                .fromDecision(fromDecision)
                .toDecision(toDecision)
                .status(s.getStatus())
                .createAt(s.getCreateAt())
                .updateAt(s.getUpdateAt())
                .build();
    }
}
