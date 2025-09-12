package com.mymatch.dto.response.product;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
    Long id;
    String name;
    String description;
    Double price;
    String imageUrl;
}
