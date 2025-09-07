package com.jakubbone.checkout.service;

import com.jakubbone.checkout.domain.CartItem;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class CheckoutService {
    private final ProductService productService;
    private final PriceCalculator priceCalculator;
    private final Map<String, CartItem> cart = new HashMap<>();

    public CheckoutService(ProductService productService, PriceCalculator priceCalculator) {
        this.productService = productService;
        this.priceCalculator = priceCalculator;
    }

    // 1. Scan
    // 2. Calculate discount
    // 3. return totalPrice

    public BigDecimal scan(String sku){
        return null;
    };

}
