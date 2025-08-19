package com.mymatch.dto.request.user;

import com.mymatch.enums.RoleType;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserFilterRequest {
    int page;
    int size;
    String sort;
    RoleType role;
    Integer deleted;
    String username;
    String email;
    String search;
}
