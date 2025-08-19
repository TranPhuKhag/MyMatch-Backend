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

    @Email
    String email;

    String firstName;
    String lastName;

    @Pattern(regexp = "^(\\+\\d{1,3}[- ]?)?\\d{9,15}$", message = "Invalid phone number")
    String phone;

    String address;
    String avatarUrl;
    String password;
}
