package com.jakubbone.checkout.service;

import com.jakubbone.checkout.domain.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
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

    public void scanItem(String sku) {
        Product product = productService.getProduct(sku);
        cart.compute(sku, (key, item) -> {
            if (item == null) {
                return new CartItem(product, 1);
            } else {
                item.addQuantity(1);
                return item;
            }
        });
    }

    public void clearCart() {
        cart.clear();
    }

    public Collection<CartItem> getCartItems() {
        return Collections.unmodifiableCollection(cart.values());
    }

    public Receipt generateReceipt() {
        List<ReceiptItem> receiptItems = new ArrayList<>();
        List<AppliedDiscount> appliedDiscounts = new ArrayList<>();
        BigDecimal subTotal = BigDecimal.ZERO;

        for (CartItem item : cart.values()) {
            BigDecimal lineTotalPrice = priceCalculator.calculatePrice(
                    item.getProduct(),
                    item.getQuantity(),
                    productService.getMultiBuyOffer(item.getProduct().sku())
            );
            BigDecimal unitPrice = lineTotalPrice.divide(BigDecimal.valueOf(item.getQuantity()), 2, RoundingMode.HALF_UP);
            receiptItems.add(new ReceiptItem(item.getProduct(), item.getQuantity(), unitPrice, lineTotalPrice));
            subTotal = subTotal.add(lineTotalPrice);
        }

        for (BundleOffer bundleOffer : productService.getBundleOffers()) {
            CartItem item1 = cart.get(bundleOffer.sku1());
            CartItem item2 = cart.get(bundleOffer.sku2());

            if (item1 != null && item2 != null) {
                int numberOfBundles = Math.min(item1.getQuantity(), item2.getQuantity());
                if (numberOfBundles > 0) {
                    BigDecimal totalDiscountForOffer = bundleOffer.discount().multiply(BigDecimal.valueOf(numberOfBundles));
                    String description = String.format("Rabat za zestaw (%s + %s)", bundleOffer.sku1(), bundleOffer.sku2());
                    appliedDiscounts.add(new AppliedDiscount(description, totalDiscountForOffer));
                }
            }
        }

        BigDecimal totalDiscounts = appliedDiscounts.stream()
                .map(AppliedDiscount::amount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal finalTotal = subTotal.subtract(totalDiscounts).setScale(2, RoundingMode.HALF_UP);

        return new Receipt(receiptItems, appliedDiscounts, finalTotal);
    }

    public Receipt checkout() {
        Receipt receipt = generateReceipt();
        clearCart();
        return receipt;
    }
}