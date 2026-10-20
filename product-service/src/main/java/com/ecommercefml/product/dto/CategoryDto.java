package com.ecommercefml.product.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDto {
    private Long id;

    @NotBlank(message = "Category name can't be empty")
    private String name;
    private String description;

    private Long parentId;
}
