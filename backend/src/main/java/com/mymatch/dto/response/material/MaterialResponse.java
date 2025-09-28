package com.mymatch.dto.response.material;

import com.mymatch.dto.response.course.CourseResponse;
import com.mymatch.dto.response.lecturer.LecturerResponse;
import com.mymatch.dto.response.user.UserResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MaterialResponse {
    Long id;
    String name;
    String description;
    Double price;
    UserResponse owner;
    CourseResponse course;
    LecturerResponse lecturer;
    Integer totalDownloads;
    Integer totalPurchases;
    LocalDateTime createAt;
    List<MaterialItemResponse> items;
    LocalDateTime updateAt;
    Boolean isPurchased = false;
}