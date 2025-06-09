package com.clotho.microservices.inventory_service.controller;

import com.clotho.microservices.inventory_service.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public boolean isAvailable(@RequestParam String productId, @RequestParam Integer quantity) {
        return inventoryService.isAvailable(productId, quantity);
    }

}
