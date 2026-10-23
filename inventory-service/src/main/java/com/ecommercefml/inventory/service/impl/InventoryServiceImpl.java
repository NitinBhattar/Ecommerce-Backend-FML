package com.ecommercefml.inventory.service.impl;

import com.ecommercefml.inventory.dto.InventoryRequestDto;
import com.ecommercefml.inventory.dto.InventoryResponseDto;
import com.ecommercefml.inventory.entity.Inventory;
import com.ecommercefml.inventory.exception.InventoryAlreadyExistsException;
import com.ecommercefml.inventory.exception.InventoryNotFoundException;
import com.ecommercefml.inventory.mapper.InventoryMapper;
import com.ecommercefml.inventory.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {
    private final InventoryRepository inventoryRepository;
    private final InventoryMapper inventoryMapper;

    @Override
    public InventoryResponseDto addInventory(InventoryRequestDto inventoryRequestDto) {
        // Check if SKU already exists
        if ( inventoryRepository.existsBySkuCode(inventoryRequestDto.getSkuCode()) ) {
            throw new InventoryAlreadyExistsException(inventoryRequestDto.getSkuCode());
        }

        Inventory inventory = inventoryMapper.toEntity(inventoryRequestDto);
        Inventory savedInventory = inventoryRepository.save(inventory);

        return inventoryMapper.toResponseDto(savedInventory);
    }

    @Override
    public InventoryResponseDto checkInventory(String skuCode) {
        Inventory inventory = inventoryRepository.findBySkuCode(skuCode).orElseThrow(() -> new InventoryNotFoundException(skuCode));

        return inventoryMapper.toResponseDto(inventory);
    }
}
