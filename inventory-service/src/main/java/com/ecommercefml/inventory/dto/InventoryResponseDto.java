package com.ecommercefml.inventory.dto;

import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryResponseDto {
    private String skuCode;
    private boolean inStock;
    private Integer availableQuantity;
}
