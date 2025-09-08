package com.jakubbone.checkout.service;

import com.jakubbone.checkout.domain.MultiBuyOffer;
import com.jakubbone.checkout.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PriceCalculatorTest {
    PriceCalculator priceCalculator;

    @BeforeEach
    void setUp() {
        priceCalculator = new PriceCalculator();
    }

    @Test
    void shouldCalculateNormalPrice() {
        Product product = new Product("A", new BigDecimal("40"));
        BigDecimal result = priceCalculator.calculatePrice(product, 2, Optional.empty());
        assertEquals(new BigDecimal("80.00"), result);
    }

    @Test
    void shouldCalculateSpecialPrice() {
        Product product = new Product("A", new BigDecimal("40"));
        MultiBuyOffer offer = new MultiBuyOffer("A", 3, new BigDecimal("30"));
        BigDecimal result = priceCalculator.calculatePrice(product, 3, Optional.of(offer));
        assertEquals(new BigDecimal("30.00"), result);
    }

    @Test
    void shouldCalculateMixedPrice() {
        Product product = new Product("A", new BigDecimal("40"));
        MultiBuyOffer offer = new MultiBuyOffer("A", 3, new BigDecimal("30"));
        BigDecimal result = priceCalculator.calculatePrice(product, 5, Optional.of(offer));
        assertEquals(new BigDecimal("110.00"), result); // 30 + 2*40
    }
}
