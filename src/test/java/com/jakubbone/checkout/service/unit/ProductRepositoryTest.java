package com.jakubbone.checkout.service.unit;


import com.jakubbone.checkout.domain.Product;
import com.jakubbone.checkout.exception.ProductNotFoundException;
import com.jakubbone.checkout.service.ProductRepository;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProductRepositoryTest {

    @Test
    void shouldThrowExceptionForUnknownProduct() {
        Map<String, Product> products = Map.of(
                "A", new Product("A", new BigDecimal("40"))
        );
        ProductRepository repository = new ProductRepository(products, Map.of(), List.of());

        assertThrows(ProductNotFoundException.class, () -> repository.getProduct("X"));
    }

    @Test
    void shouldReturnProduct() {
        Product productA = new Product("A", new BigDecimal("40"));
        Map<String, Product> products = Map.of("A", productA);
        ProductRepository repository = new ProductRepository(products, Map.of(), List.of());

        Product result = repository.getProduct("A");
        assertEquals(productA, result);
    }
}
