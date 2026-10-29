package com.ecommercefml.inventory.controller;

import com.ecommercefml.inventory.dto.ApiResponse;
import com.ecommercefml.inventory.dto.InventoryRequestDto;
import com.ecommercefml.inventory.dto.InventoryResponseDto;
import com.ecommercefml.inventory.service.impl.InventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/inventory")
public class InventoryController {
    private final InventoryService inventoryService;

    @PostMapping("/add")
    ResponseEntity<ApiResponse<InventoryResponseDto>> addInventory(@Valid @RequestBody InventoryRequestDto inventoryRequestDto) {
        InventoryResponseDto inventoryResponseDto = inventoryService.addInventory(inventoryRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>("Inventory added successfully", inventoryResponseDto));
    }

    @GetMapping("/{skuCode}")
    ResponseEntity<InventoryResponseDto> checkInventory(@PathVariable String skuCode) {
        InventoryResponseDto inventoryResponseDto = inventoryService.checkInventory(skuCode);

        return ResponseEntity.ok(inventoryResponseDto);
    }
}
