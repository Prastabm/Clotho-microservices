package com.clotho.microservices.inventory_service.repository;

import com.clotho.microservices.inventory_service.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    boolean existsByProductIdAndQuantityGreaterThanEqual(String productId, Integer quantity);
}
