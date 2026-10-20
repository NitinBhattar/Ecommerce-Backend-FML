package com.ecommercefml.product.dto;

import com.ecommercefml.product.validation.ValidDiscountedPrice;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ValidDiscountedPrice
public class CreateProductDto {
    @NotBlank(message = "Product name is required")
    private String name;
    private String description;

    @Positive(message = "Price must be positive")
    private BigDecimal price;
    private BigDecimal discountedPrice;

    @Min(value = 0, message = "Quantity must be 0 or more")
    private int quantity;
    private String brand;
    private String imageUrl;

    @NotNull(message = "Category id is required")
    private Long categoryId;
}
