package com.mymatch.utils;

import com.mymatch.exception.AppException;
import com.mymatch.exception.ErrorCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

public class SecurityUtil {
    /**
     * Get the current user's ID (Long) from JWT in the SecurityContext
     *
     * @return User ID
     */
    public static Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof Jwt jwt) {
            Long userId = jwt.getClaim("userId");
            if (userId != null) {
                return userId;
            }
        }

        throw new AppException(ErrorCode.UNAUTHENTICATED);
    }
    /**
     * Check if the current user has a specific authority
     *
     * @param authority The authority to check for
     * @return true if the user has the authority, false otherwise
     */
    public static boolean hasAuthority(String authority) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null &&
                authentication.getAuthorities().stream()
                        .anyMatch(a -> a.getAuthority().equals(authority));
    }

}
