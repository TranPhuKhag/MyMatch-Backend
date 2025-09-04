package com.mymatch.service.impl;

import com.mymatch.dto.request.wallet.WalletRequest;
import com.mymatch.dto.response.transaction.TransactionResponse;
import com.mymatch.dto.response.wallet.WalletResponse;
import com.mymatch.entity.Transaction;
import com.mymatch.entity.User;
import com.mymatch.entity.Wallet;
import com.mymatch.enums.TransactionSource;
import com.mymatch.enums.TransactionType;
import com.mymatch.exception.AppException;
import com.mymatch.exception.ErrorCode;
import com.mymatch.mapper.TransactionMapper;
import com.mymatch.mapper.WalletMapper;
import com.mymatch.repository.UserRepository;
import com.mymatch.repository.WalletRepository;
import com.mymatch.service.TransactionService;
import com.mymatch.service.WalletService;
import com.mymatch.utils.SecurityUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WalletServiceImpl implements WalletService {

    UserRepository userRepository;
    WalletRepository walletRepository;
    WalletMapper walletMapper;
    TransactionMapper transactionMapper;
    TransactionService transactionService;

    private static final double EXCHANGE_RATE = 1000.0;

    @Override
    public WalletResponse getWallet() {
        Wallet wallet = userRepository
                .findById(SecurityUtil.getCurrentUserId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND))
                .getWallet();
        return walletMapper.toResponse(wallet);
    }

    @Override
    @Transactional
    public TransactionResponse topUpWallet(Double amountVND) {
        if (amountVND == null || amountVND <= 0) {
            throw new AppException(ErrorCode.INVALID_PARAMETER);
        }

        Wallet wallet = userRepository
                .findById(SecurityUtil.getCurrentUserId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND))
                .getWallet();
        long coinAmount = (long) (amountVND / EXCHANGE_RATE);

        Transaction transaction = transactionService.initiateTransaction(
                wallet, coinAmount, amountVND,
                TransactionType.IN,
                TransactionSource.TOP_UP,
                "User requested to top up " + amountVND + " VND"
        );
        try {
            wallet.setCoin(wallet.getCoin() + coinAmount);
            walletRepository.save(wallet);

            transactionService.markAsCompleted(transaction);
        } catch (Exception e) {
            log.error("Top-up failed for transaction {}. Reason: {}", transaction.getTransactionCode(), e.getMessage());
            transactionService.markAsFailed(transaction, e.getMessage());
        }
        return transactionMapper.toResponse(transaction);
    }


    @Override
    @Transactional
    public void addToCoinWallet(WalletRequest request) {
        Wallet wallet = getUser(request.getUserId()).getWallet();
        Long coinAmount = request.getCoin();
        wallet.setCoin(wallet.getCoin() + coinAmount);
        walletRepository.save(wallet);
        Transaction transaction = transactionService.initiateTransaction(wallet, coinAmount, null, TransactionType.IN, request.getSource(), request.getDescription());
        transactionService.markAsCompleted(transaction);

        log.info("Added {} coins to wallet of user {}. Source: {}", coinAmount, wallet.getUser().getId(), request.getSource());
    }

    @Override
    @Transactional
    public void deductFromWallet(WalletRequest request) {
        Wallet wallet = getUser(request.getUserId()).getWallet();
        Long coinAmount = request.getCoin();

        if (wallet.getCoin() < coinAmount) {
            throw new AppException(ErrorCode.INSUFFICIENT_FUNDS);
        }

        long balanceBefore = wallet.getCoin();
        long balanceAfter = balanceBefore - coinAmount;

        wallet.setCoin(balanceAfter);
        walletRepository.save(wallet);
        Transaction transaction = transactionService.initiateTransaction(wallet, coinAmount, null, TransactionType.OUT, request.getSource(), request.getDescription());
        transactionService.markAsCompleted(transaction);
    }

    private User getUser(Long userId) {

        return userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
    }
}