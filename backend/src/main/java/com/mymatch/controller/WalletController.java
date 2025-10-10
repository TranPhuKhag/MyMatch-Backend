package com.mymatch.controller;

import com.mymatch.dto.response.ApiResponse;
import com.mymatch.dto.response.wallet.WalletResponse;
import com.mymatch.service.WalletService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wallets")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WalletController {
    WalletService walletService;
    @GetMapping("/me")
    public ApiResponse<WalletResponse> getMyWallet() {
        return ApiResponse.<WalletResponse>builder()
                .code(HttpStatus.OK.value())
                .result(walletService.getWallet())
                .message("Lấy ví thành công")
                .build();
    }
}
