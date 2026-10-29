package com.ecommercefml.inventory.repository;

import com.ecommercefml.inventory.entity.ProcessedOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcessedOrderRepository extends JpaRepository<ProcessedOrder, Long> {
    boolean existsByOrderId(String orderId);
}
