package com.clotho.microservices.client;


import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;


public interface InventoryClient {

    @GetExchange("/api/inventory")
    boolean isAvailable(@RequestParam String productId,
                        @RequestParam Integer quantity);
}
