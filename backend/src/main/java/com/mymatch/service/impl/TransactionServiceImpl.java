package com.mymatch.service.impl;

import com.mymatch.entity.Transaction;
import com.mymatch.entity.Wallet;
import com.mymatch.enums.TransactionSource;
import com.mymatch.enums.TransactionStatus;
import com.mymatch.enums.TransactionType;
import com.mymatch.repository.TransactionRepository;
import com.mymatch.service.TransactionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TransactionServiceImpl implements TransactionService {
    TransactionRepository transactionRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Transaction initiateTransaction(Wallet wallet, Long coin, Double amountVND, TransactionType type, TransactionSource source, String description) {
        Transaction transaction = Transaction.builder()
                .wallet(wallet)
                .coin(coin)
                .amount(amountVND)
                .type(type)
                .source(source)
                .description(description)
                .status(TransactionStatus.PENDING)
                .build();
        return transactionRepository.save(transaction);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void markAsCompleted(Transaction transaction) {
        transaction.setStatus(TransactionStatus.COMPLETED);
        transactionRepository.save(transaction);

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void markAsFailed(Transaction transaction, String reason) {
        transaction.setStatus(TransactionStatus.FAILED);
        transaction.setDescription(transaction.getDescription() + " | Failed reason: " + reason);
        transactionRepository.save(transaction);

    }
}
