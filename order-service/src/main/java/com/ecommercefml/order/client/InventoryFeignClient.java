package com.ecommercefml.order.client;

import com.ecommercefml.order.dto.InventoryResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// No hard coding of url of inventory-service, it will fetch it using eureka
@FeignClient(name = "inventory-service")
public interface InventoryFeignClient {
    @GetMapping("/api/inventory/{skuCode}")
    InventoryResponseDto isInStock(@PathVariable String skuCode);
}
