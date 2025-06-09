package com.clotho.microservices.dto;

import org.antlr.v4.runtime.misc.NotNull;

import java.math.BigDecimal;

public record OrderRequest(
        String productId,
        @NotNull Integer quantity, // changed from int to Integer
        String customerId,
        UserDetails userDetails,
        String shippingAddress,
        BigDecimal totalPrice
) {
    public record UserDetails(String email, String firstName, String lastName) {
    }
}
