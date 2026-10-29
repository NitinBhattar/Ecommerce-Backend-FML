package com.ecommercefml.inventory.service.impl;

import com.ecommercefml.inventory.dto.InventoryRequestDto;
import com.ecommercefml.inventory.dto.InventoryResponseDto;
import com.ecommercefml.inventory.event.OrderPlacedEvent;

public interface InventoryService {
    public InventoryResponseDto addInventory(InventoryRequestDto inventoryRequestDto);
    public InventoryResponseDto checkInventory(String skuCode);
    public void updateInventory(OrderPlacedEvent orderPlacedEvent);
}
