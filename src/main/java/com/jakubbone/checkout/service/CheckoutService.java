package com.jakubbone.checkout.service;

import com.jakubbone.checkout.domain.CartItem;
import com.jakubbone.checkout.domain.Product;
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

    public void scanItem(String sku){
        Product product = productService.getProduct(sku);

        CartItem existingItem = cart.get(sku);
        if (existingItem != null) {
            existingItem.addQuantity(1);
        } else {
            cart.put(sku, new CartItem(product, 1));
        }
    }




}
