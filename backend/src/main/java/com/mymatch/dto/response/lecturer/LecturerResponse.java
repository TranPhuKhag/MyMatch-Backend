package com.mymatch.dto.response.lecturer;

import com.mymatch.dto.response.campus.CampusResponse;
import com.mymatch.dto.response.tag.TagResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LecturerResponse {
    Long id;
    String name;
    String code;
    String bio;
    CampusResponse campus;
    List<TagResponse> tags;
    int reviewCount;
}
