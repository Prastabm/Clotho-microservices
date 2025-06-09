package com.clotho.microservices.inventory_service.service;

import com.clotho.microservices.inventory_service.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    public boolean isAvailable(String productId, Integer quantity) {
        return inventoryRepository.existsByProductIdAndQuantityGreaterThanEqual(productId, quantity);
    }
}
