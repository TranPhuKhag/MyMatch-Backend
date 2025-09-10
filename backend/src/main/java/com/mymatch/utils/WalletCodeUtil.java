package com.mymatch.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WalletCodeUtil {
    @Value("${mymatch.wallet-code.prefix}")    String prefix;
    @Value("${mymatch.wallet-code.suffix}")      String suffix;
    @Value("${mymatch.wallet-code.min-digits}") int minDigits;
    @Value("${mymatch.wallet-code.max-digits}") int maxDigits;

    private static final java.security.SecureRandom RNG = new java.security.SecureRandom();

    public String randomBase() {
        int len = Math.max(minDigits, Math.min(maxDigits, 6));
        StringBuilder digits = new StringBuilder(len);
        for (int i = 0; i < len; i++) digits.append(RNG.nextInt(10));
        if (digits.charAt(0) == '0') digits.setCharAt(0, (char)('1' + RNG.nextInt(9)));
        return (prefix + digits).toUpperCase(); // Ví dụ: MM123456
    }

    public String toFull(String base) {
        return (suffix == null || suffix.isBlank()) ? base : (base + suffix).toUpperCase(); // MM123456MYM
    }
}
