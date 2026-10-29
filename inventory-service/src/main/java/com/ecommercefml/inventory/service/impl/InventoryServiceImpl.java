package com.ecommercefml.inventory.service.impl;

import com.ecommercefml.inventory.dto.InventoryRequestDto;
import com.ecommercefml.inventory.dto.InventoryResponseDto;
import com.ecommercefml.inventory.entity.Inventory;
import com.ecommercefml.inventory.entity.ProcessedOrder;
import com.ecommercefml.inventory.event.OrderPlacedEvent;
import com.ecommercefml.inventory.exception.InventoryAlreadyExistsException;
import com.ecommercefml.inventory.exception.InventoryNotFoundException;
import com.ecommercefml.inventory.exception.OrderAlreadyProcessedException;
import com.ecommercefml.inventory.exception.OrderedQuantityExceededException;
import com.ecommercefml.inventory.mapper.InventoryMapper;
import com.ecommercefml.inventory.repository.InventoryRepository;
import com.ecommercefml.inventory.repository.ProcessedOrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {
    private final InventoryRepository inventoryRepository;
    private final ProcessedOrderRepository processedOrderRepository;
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

    // @Transactional for race condition, it makes this atomic
    @Transactional
    @Override
    public void updateInventory(OrderPlacedEvent orderPlacedEvent) {
        Inventory inventory = inventoryRepository.findBySkuCode(orderPlacedEvent.getSkuCode()).orElseThrow(() -> new InventoryNotFoundException(orderPlacedEvent.getSkuCode()));

        final String orderId = orderPlacedEvent.getOrderId();

        // No value in data
        if (orderId == null || orderId.isBlank()) {
            throw new IllegalArgumentException("Order id is missing");
        }

        // Check for idempotency
        if (processedOrderRepository.existsByOrderId(orderId)) {
            throw new OrderAlreadyProcessedException(orderId);
        }

        final Integer availableQuantity = inventory.getQuantity();
        final Integer orderedQuantity = orderPlacedEvent.getQuantity();

        // No value in data
        if (orderedQuantity == null || orderedQuantity <= 0) {
            throw new IllegalArgumentException("Ordered quantity must be greater than zero");
        }

        // Ordered quantity is greater than available quantity
        if (availableQuantity < orderedQuantity) {
            throw new OrderedQuantityExceededException(orderPlacedEvent.getSkuCode());
        }

        inventory.setQuantity(availableQuantity - orderedQuantity);
        inventoryRepository.save(inventory);

        // Marking order as processed
        ProcessedOrder processedOrder = new ProcessedOrder();
        processedOrder.setOrderId(orderId);

        processedOrderRepository.save(processedOrder);
    }
}
