package com.jakubbone.checkout.controller;

import com.jakubbone.checkout.domain.CartItem;
import com.jakubbone.checkout.domain.Receipt;
import com.jakubbone.checkout.service.CheckoutService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/checkout")
public class CheckoutController {
    private final CheckoutService checkoutService;

    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @PostMapping("/scan/{sku}")
    public ResponseEntity<Void> scanItem(@PathVariable String sku) {
        if (sku == null || sku.isBlank()) {
            throw new IllegalArgumentException("SKU cannot be empty");
        }
        checkoutService.scanItem(sku);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/cart")
    public ResponseEntity<Collection<CartItem>> getCart() {
        return ResponseEntity.ok(checkoutService.getCartItems());
    }

    @PostMapping("/checkout")
    public ResponseEntity<Receipt> checkout() {
        return ResponseEntity.ok(checkoutService.checkout());
    }
}
