package com.ecommercefml.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryResponseDto {
    private String skuCode;
    private boolean inStock;
    private Integer availableQuantity;
}
