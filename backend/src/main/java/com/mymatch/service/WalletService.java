package com.mymatch.service;

import com.mymatch.dto.request.wallet.WalletRequest;
import com.mymatch.dto.response.transaction.TransactionResponse;
import com.mymatch.dto.response.wallet.WalletResponse;
import com.mymatch.entity.Transaction;


public interface WalletService {

    /**
     * Lấy thông tin ví của user hiện tại
     *
     * @return WalletResponse - thông tin ví (số coin, balance...)
     */
    WalletResponse getWallet();

    /**
     * Nạp tiền vào ví (chuyển VND thành coin)
     *
     * @param amount - số tiền VND cần nạp
     * @return TransactionResponse - thông tin giao dịch nạp tiền
     */
    TransactionResponse topUpWallet(Double amount);

    /**
     * Cộng coin vào ví (từ hệ thống - reward, bonus...)
     *
     * @param walletRequest - thông tin coin cần cộng và lý do
     */
    Transaction addToCoinWallet(WalletRequest walletRequest);

    /**
     * Trừ coin từ ví (thanh toán, mua feature...)
     *
     * @param walletRequest - thông tin coin cần trừ và lý do
     */
    Transaction deductFromWallet(WalletRequest walletRequest);
}
