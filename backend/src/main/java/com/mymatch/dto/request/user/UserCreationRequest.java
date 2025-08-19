package com.mymatch.dto.request.user;

import com.mymatch.dto.request.student.StudentCreationRequest;
import com.mymatch.enums.RoleType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
    @NotBlank
    String username;

    @NotBlank
    String password;

    @Email
    @NotBlank
    String email;

    String firstName;
    String lastName;

    @Pattern(regexp = "^(\\+\\d{1,3}[- ]?)?\\d{9,15}$", message = "Invalid phone number")
    String phone;

    String address;
    String avatarUrl;
    RoleType roleType = RoleType.STUDENT; // Default role type is STUDENT

    @Valid
    StudentCreationRequest studentCreationRequest; // Optional, only for STUDENT role
}
