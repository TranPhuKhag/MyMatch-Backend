package com.mymatch.service;

import com.mymatch.dto.response.transaction.TransactionResponse;
import com.mymatch.entity.Transaction;
import com.mymatch.entity.Wallet;
import com.mymatch.enums.TransactionSource;
import com.mymatch.enums.TransactionType;

public interface TransactionService {
    /**
     * Khởi tạo một giao dịch ở trạng thái PENDING.
     * Method này sẽ chạy trong một transaction độc lập.
     */
    Transaction initiateTransaction(Wallet wallet, Long coin, Double amountVND, TransactionType type, TransactionSource source, String description);

    /**
     * Đánh dấu giao dịch là đã HOÀN THÀNH.
     */
    TransactionResponse markAsCompleted(Transaction transaction);

    /**
     * Đánh dấu giao dịch là đã THẤT BẠI.
     */
    TransactionResponse markAsFailed(Transaction transaction, String reason);

    void rollbackTransaction(Transaction transaction, String reason);
}
