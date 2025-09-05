package com.jakubbone.checkout.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CheckoutService {
    private final ProductService productService;

    public CheckoutService(ProductService productService) {
        this.productService = productService;
    }

    // 1. Scan
    // 2. Calculate discount
    // 3. return totalPrice

    public BigDecimal scan(String sku){
        return null;
    };

}
