package com.ecommercefml.inventory.mapper;

import com.ecommercefml.inventory.dto.InventoryRequestDto;
import com.ecommercefml.inventory.dto.InventoryResponseDto;
import com.ecommercefml.inventory.entity.Inventory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InventoryMapper {
    @Mapping(target = "id", ignore = true)
    Inventory toEntity(InventoryRequestDto inventoryRequestDto);

    @Mapping(target = "inStock", expression = "java(inventory.getQuantity() != null && inventory.getQuantity() > 0)")
    @Mapping(target = "availableQuantity", source = "quantity")
    InventoryResponseDto toResponseDto(Inventory inventory);
}
