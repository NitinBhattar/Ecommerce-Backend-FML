package com.ecommercefml.payment.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "inventory-service")
public interface InventoryClient {
    @PostMapping("/api/inventory/{orderId}/commit")
    void commitInventory(@PathVariable String orderId);

    @PostMapping("/api/inventory/{orderId}/rollback")
    void rollbackInventory(@PathVariable String orderId);
}
