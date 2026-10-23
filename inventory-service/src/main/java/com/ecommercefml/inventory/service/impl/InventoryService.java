package com.ecommercefml.inventory.service.impl;

import com.ecommercefml.inventory.dto.InventoryRequestDto;
import com.ecommercefml.inventory.dto.InventoryResponseDto;

public interface InventoryService {
    public InventoryResponseDto addInventory(InventoryRequestDto inventoryRequestDto);
    public InventoryResponseDto checkInventory(String skuCode);
}
