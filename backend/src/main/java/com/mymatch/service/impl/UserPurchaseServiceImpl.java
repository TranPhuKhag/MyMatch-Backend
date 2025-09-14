package com.mymatch.service.impl;

import com.mymatch.dto.request.purchase.UserPurchaseCreateRequest;
import com.mymatch.dto.request.purchase.UserPurchaseFilterRequest;
import com.mymatch.dto.request.wallet.WalletRequest;
import com.mymatch.dto.response.PageResponse;
import com.mymatch.dto.response.purchase.UserPurchaseResponse;
import com.mymatch.entity.Plan;
import com.mymatch.entity.User;
import com.mymatch.entity.UserPurchase;
import com.mymatch.entity.Wallet;
import com.mymatch.enums.PurchaseStatus;
import com.mymatch.enums.TransactionSource;
import com.mymatch.enums.TransactionType;
import com.mymatch.exception.AppException;
import com.mymatch.exception.ErrorCode;
import com.mymatch.mapper.UserPurchaseMapper;
import com.mymatch.repository.PlanRepository;
import com.mymatch.repository.UserPurchaseRepository;
import com.mymatch.repository.UserRepository;
import com.mymatch.repository.WalletRepository;
import com.mymatch.service.UserPurchaseService;
import com.mymatch.service.WalletService;
import com.mymatch.specification.UserPurchaseSpecification;
import com.mymatch.utils.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;

import static com.mymatch.utils.SecurityUtil.hasAuthority;
import static lombok.AccessLevel.PRIVATE;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class UserPurchaseServiceImpl implements UserPurchaseService {

    UserRepository userRepository;
    PlanRepository planRepository;
    UserPurchaseRepository userPurchaseRepository;
    UserPurchaseMapper userPurchaseMapper;
    WalletRepository walletRepository;
    WalletService walletService;

    @Override
    @Transactional
    public UserPurchaseResponse create(UserPurchaseCreateRequest request) {
        User user = userRepository.findById(SecurityUtil.getCurrentUserId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        Plan plan = planRepository.findById(request.getPlanId())
                .orElseThrow(() -> new AppException(ErrorCode.PLAN_NOT_FOUND));

        userPurchaseRepository.findFirstByUserIdAndPlanIdAndStatus(
                user.getId(), plan.getId(), PurchaseStatus.ACTIVE
        ).ifPresent(up -> { throw new AppException(ErrorCode.PURCHASE_ALREADY_ACTIVE); });

        WalletRequest walletRequest = WalletRequest.builder()
                .userId(user.getId())
                .type(TransactionType.OUT)
                .source(TransactionSource.SERVICE_PURCHASE)
                .coin(plan.getCoin())
                .description("Mua gói " + plan.getName())
                .build();
        walletService.deductFromWallet(walletRequest);

        LocalDateTime expiryDate = LocalDateTime.now().plusDays(plan.getDurationDays());
        UserPurchase userPurchase = UserPurchase.builder()
                .user(user)
                .plan(plan)
                .expiryDate(expiryDate)
                .costCoin(plan.getCoin())
                .status(PurchaseStatus.ACTIVE)
                .purchaseDate(LocalDateTime.now())
                .build();

        userPurchase = userPurchaseRepository.save(userPurchase);

        plan.setPurchaseCount(plan.getPurchaseCount() + 1);
        planRepository.save(plan);

        return userPurchaseMapper.toResponse(userPurchase);
    }

    @Override
    public PageResponse<UserPurchaseResponse> getAll(UserPurchaseFilterRequest filterRequest,
                                                     int page,
                                                     int size,
                                                     String sortBy,
                                                     String sortDirection) {
        Sort.Direction direction = Sort.Direction.fromOptionalString(sortDirection)
                .orElse(Sort.Direction.DESC);
        if (!hasAuthority("user_purchase:read")) {
            filterRequest.setUserId(SecurityUtil.getCurrentUserId());
        }

            Sort sort = Sort.by(direction, (sortBy != null && !sortBy.isBlank()) ? sortBy : "createAt");

        Pageable pageable = PageRequest.of(
                Math.max(page - 1, 0),
                Math.max(size, 1),
                sort
        );


        Page<UserPurchase> pages = userPurchaseRepository.findAll(
                UserPurchaseSpecification.byFilter(filterRequest),
                pageable
        );

        var data = pages.getContent().stream()
                .map(userPurchaseMapper::toResponse)
                .toList();

        return PageResponse.<UserPurchaseResponse>builder()
                .data(data)
                .pageSize(pages.getSize())
                .totalPages(pages.getTotalPages())
                .totalElements(pages.getTotalElements())
                .currentPage(page)
                .build();
    }
}
