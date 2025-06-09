package com.clotho.microservices.service;

import com.clotho.microservices.client.InventoryClient;
import com.clotho.microservices.dto.OrderRequest;
import com.clotho.microservices.events.OrderPlacedEvent;
import com.clotho.microservices.model.Order;
import com.clotho.microservices.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final InventoryClient inventoryClient;

    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

    public void placeOrder( OrderRequest orderRequest) {

        boolean isProductAvailable = inventoryClient.isAvailable(orderRequest.productId(), orderRequest.quantity());

        if (isProductAvailable) {
            System.out.println(orderRequest);
            Order order = new Order();
            order.setOrderId(UUID.randomUUID().toString());
            order.setProductId(orderRequest.productId());
            order.setQuantity(orderRequest.quantity());
            order.setCustomerId(orderRequest.customerId());
            order.setShippingAddress(orderRequest.shippingAddress());
            order.setAmount(orderRequest.totalPrice());
            orderRepository.save(order);

            //send messsage to kafka topic
            //ordernum, email
            OrderPlacedEvent orderPlacedEvent = new OrderPlacedEvent();
            orderPlacedEvent.setOrderNumber(order.getOrderId());
            orderPlacedEvent.setEmail(orderRequest.userDetails().email());
            orderPlacedEvent.setFirstName(orderRequest.userDetails().firstName());
            orderPlacedEvent.setLastName(orderRequest.userDetails().lastName());
            log.info("Start: Sending order placed event to kafka topic: {}", orderPlacedEvent);
            kafkaTemplate.send("orderplaced", orderPlacedEvent);
            log.info("End: Sending order placed event to kafka topic: {}", orderPlacedEvent);

        }
        else {
            throw new RuntimeException("Product is not available in inventory");
        }
    }
    public Order getOrderById(String orderId){
        try {
            return orderRepository.findByOrderId(orderId);
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }
    }

}
