package com.ecommercefml.inventory.repository;

import com.ecommercefml.inventory.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    boolean existsBySkuCode(String skuCode);
    Optional<Inventory> findBySkuCode(String skuCode);
}
