package com.clotho.microservices.repository;


import com.clotho.microservices.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, String> {
    Order findByOrderId(String orderId);
}