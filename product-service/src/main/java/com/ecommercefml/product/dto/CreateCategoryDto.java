package com.ecommercefml.product.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateCategoryDto {
    @NotBlank(message = "Category name can't be empty")
    private String name;
    private String description;

    private Long parentId;
}
