package com.jakubbone.checkout.service;

import com.jakubbone.checkout.domain.CartItem;
import com.jakubbone.checkout.domain.Receipt;
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
    private final ProductRepository productRepository;
    private final PriceCalculator priceCalculator;
    private final Map<String, com.jakubbone.checkout.domain.CartItem> cart = new HashMap<>();

    public CheckoutService(ProductRepository productRepository, PriceCalculator priceCalculator) {
        this.productRepository = productRepository;
        this.priceCalculator = priceCalculator;
    }

    public void scanItem(String sku) {
        com.jakubbone.checkout.domain.Product product = productRepository.getProduct(sku);
        cart.compute(sku, (key, item) -> {
            if (item == null) {
                return new com.jakubbone.checkout.domain.CartItem(product, 1);
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
        List<Receipt.Item> pricedItems = getPricedReceiptItems();
        List<Receipt.Discount> bundleDiscounts = getAppliedBundleDiscounts();

        BigDecimal subTotal = BigDecimal.ZERO;
        for (Receipt.Item item : pricedItems) {
            subTotal = subTotal.add(item.totalPrice());
        }

        BigDecimal totalDiscounts = BigDecimal.ZERO;
        for (Receipt.Discount discount : bundleDiscounts) {
            totalDiscounts = totalDiscounts.add(discount.amount());
        }

        BigDecimal finalTotal = subTotal.subtract(totalDiscounts).setScale(2, RoundingMode.HALF_UP);

        return new Receipt(pricedItems, bundleDiscounts, finalTotal);
    }

    private List<Receipt.Item> getPricedReceiptItems() {
        List<Receipt.Item> receiptItems = new ArrayList<>();
        for (com.jakubbone.checkout.domain.CartItem item : cart.values()) {
            BigDecimal lineTotalPrice = priceCalculator.calculatePrice(
                    item.getProduct(),
                    item.getQuantity(),
                    productRepository.getMultiBuyOffer(item.getProduct().sku())
            );
            BigDecimal unitPrice = lineTotalPrice.divide(BigDecimal.valueOf(item.getQuantity()), 2, RoundingMode.HALF_UP);
            receiptItems.add(new Receipt.Item(item.getProduct(), item.getQuantity(), unitPrice, lineTotalPrice));
        }
        return receiptItems;
    }

    private List<Receipt.Discount> getAppliedBundleDiscounts() {
        List<Receipt.Discount> appliedDiscounts = new ArrayList<>();
        for (com.jakubbone.checkout.domain.BundleOffer bundleOffer : productRepository.getBundleOffers()) {
            com.jakubbone.checkout.domain.CartItem item1 = cart.get(bundleOffer.sku1());
            com.jakubbone.checkout.domain.CartItem item2 = cart.get(bundleOffer.sku2());

            if (item1 != null && item2 != null) {
                int numberOfBundles = Math.min(item1.getQuantity(), item2.getQuantity());
                if (numberOfBundles > 0) {
                    BigDecimal totalDiscountForOffer = bundleOffer.discount().multiply(BigDecimal.valueOf(numberOfBundles));
                    String description = String.format("Rabat za zestaw (%s + %s)", bundleOffer.sku1(), bundleOffer.sku2());
                    appliedDiscounts.add(new Receipt.Discount(description, totalDiscountForOffer));
                }
            }
        }
        return appliedDiscounts;
    }

    public Receipt checkout() {
        Receipt receipt = generateReceipt();
        clearCart();
        return receipt;
    }
}
