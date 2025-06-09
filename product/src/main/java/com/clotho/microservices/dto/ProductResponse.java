package com.clotho.microservices.dto;

public record ProductResponse(String id, String name, String description,
                              String price, String category, String imageUrl) {
    // This record can be used to transfer product data in responses
    // Additional validation or transformation logic can be added if needed
}
