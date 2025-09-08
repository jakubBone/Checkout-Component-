package com.jakubbone.checkout.service.unit;

import com.jakubbone.checkout.domain.BundleOffer;
import com.jakubbone.checkout.domain.Product;
import com.jakubbone.checkout.domain.Receipt;
import com.jakubbone.checkout.service.CheckoutService;
import com.jakubbone.checkout.service.PriceCalculator;
import com.jakubbone.checkout.service.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CheckoutServiceTest {
    @Mock
    private ProductRepository productRepository;

    @Mock
    private PriceCalculator priceCalculator;

    private CheckoutService checkoutService;

    @BeforeEach
    void setUp() {
        checkoutService = new CheckoutService(productRepository, priceCalculator);
    }

    @Test
    void shouldScanItem() {
        Product productA = new Product("A", new BigDecimal("40"));
        when(productRepository.getProduct("A")).thenReturn(productA);

        checkoutService.scanItem("A");

        assertEquals(1, checkoutService.getCartItems().size());
        assertEquals(1, checkoutService.getCartItems().get(0).getQuantity());
    }

}
