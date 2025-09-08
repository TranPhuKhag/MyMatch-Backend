package com.mymatch.dto.response.user;

import java.util.Set;

import com.mymatch.dto.response.lecturer.LecturerResponse;
import com.mymatch.dto.response.student.StudentResponse;
import com.mymatch.dto.response.wallet.WalletResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    Long id;
    String username;
    String email;
    String firstName;
    String lastName;
    String phone;
    String avatarUrl;
    String address;
    String role;
    Set<String> permissions;
    int deleted;
    StudentResponse student;
    WalletResponse wallet;
    LecturerResponse lecturer;
}
