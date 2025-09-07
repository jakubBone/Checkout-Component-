package com.jakubbone.checkout.domain;

import java.math.BigDecimal;
import java.util.List;

public record Receipt(
    List<Item> items,
    List<Discount> discounts,
    BigDecimal finalTotal
) {
    public record Item(Product product, int quantity, BigDecimal unitPrice, BigDecimal totalPrice) {}

    public record Discount(String description, BigDecimal amount) {}
}