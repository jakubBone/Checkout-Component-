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

    public Receipt checkout() {
        Receipt receipt = generateReceipt();
        clearCart();
        return receipt;
    }

    public Receipt generateReceipt() {
        List<ReceiptItem> multiBuyItems = getMultiBuyItems();
        List<AppliedDiscount> bundleDiscounts = getBundleDiscounts();

        BigDecimal subTotal = BigDecimal.ZERO;
        for (ReceiptItem item : multiBuyItems) {
            subTotal = subTotal.add(item.totalPrice());
        }

        BigDecimal totalDiscounts = BigDecimal.ZERO;
        for (AppliedDiscount discount : bundleDiscounts) {
            totalDiscounts = totalDiscounts.add(discount.amount());
        }

        BigDecimal finalTotal = subTotal.subtract(totalDiscounts).setScale(2, RoundingMode.HALF_UP);

        return new Receipt(multiBuyItems, bundleDiscounts, finalTotal);
    }

    private List<ReceiptItem> getMultiBuyItems() {
        List<ReceiptItem> receiptItems = new ArrayList<>();
        for (CartItem item : cart.values()) {
            BigDecimal lineTotalPrice = priceCalculator.calculatePrice(
                    item.getProduct(),
                    item.getQuantity(),
                    productService.getMultiBuyOffer(item.getProduct().sku())
            );
            BigDecimal unitPrice = lineTotalPrice.divide(BigDecimal.valueOf(item.getQuantity()), 2, RoundingMode.HALF_UP);
            receiptItems.add(new ReceiptItem(item.getProduct(), item.getQuantity(), unitPrice, lineTotalPrice));
        }
        return receiptItems;
    }

    private List<AppliedDiscount> getBundleDiscounts() {
        List<AppliedDiscount> appliedDiscounts = new ArrayList<>();
        for (BundleOffer bundleOffer : productService.getBundleOffers()) {
            CartItem item1 = cart.get(bundleOffer.sku1());
            CartItem item2 = cart.get(bundleOffer.sku2());

            if (item1 != null && item2 != null) {
                int numberOfBundles = Math.min(item1.getQuantity(), item2.getQuantity());
                if (numberOfBundles > 0) {
                    BigDecimal totalDiscountForOffer = bundleOffer.discount().multiply(BigDecimal.valueOf(numberOfBundles));
                    String description = String.format("Bundle discount for (%s + %s)", bundleOffer.sku1(), bundleOffer.sku2());
                    appliedDiscounts.add(new AppliedDiscount(description, totalDiscountForOffer));
                }
            }
        }
        return appliedDiscounts;
    }

    public void clearCart() {
        cart.clear();
    }

    public Collection<CartItem> getCartItems() {
        return Collections.unmodifiableCollection(cart.values());
    }
}
