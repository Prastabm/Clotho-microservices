package com.clotho.microservices.controller;

import com.clotho.microservices.dto.OrderRequest;
import com.clotho.microservices.model.Order;
import com.clotho.microservices.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> placeOrder(@RequestBody OrderRequest orderRequest) {
        orderService.placeOrder(orderRequest);

        try {
            return ResponseEntity.ok("Order placed successfully");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @GetMapping("/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Order> getOrderById(@PathVariable String orderId) {
        // Logic to retrieve all orders
        try {
            return ResponseEntity.ok(orderService.getOrderById(orderId));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
