package com.mymatch.dto.request.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
    String username;

    @Pattern(regexp = "^(?:\\+84|0)\\d(?:[\\s.-]?\\d){8}$", message = "Số điện thoại không hợp lệ")
    String phone;
    String avatarUrl;
    Long campusId;
    String major;
}
