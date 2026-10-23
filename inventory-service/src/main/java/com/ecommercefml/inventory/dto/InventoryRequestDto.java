package com.ecommercefml.inventory.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InventoryRequestDto {
    @NotBlank(message = "SKU code cannot be blank")
    @NotNull(message = "SKU code cannot be null")
    private String skuCode;

    @NotNull(message = "Quantity cannot be null")
    @Min(value = 0, message = "Quantity must be 0 or greater")
    private Integer quantity;
}
