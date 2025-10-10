package com.mymatch.dto.response.plan;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlanResponse {
    Long id;
    String name;
    String description;
    Double coin;
    int purchaseCount;
    String imageUrl;
    int durationDays;
}