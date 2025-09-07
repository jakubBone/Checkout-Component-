package com.jakubbone.checkout.service;

import com.jakubbone.checkout.domain.BundleOffer;
import com.jakubbone.checkout.domain.CartItem;
import com.jakubbone.checkout.domain.Product;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.Collections;
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

    public BigDecimal calculateTotal() {
        // Calculate the total price with multi-buy offers applied
        BigDecimal subTotal = BigDecimal.ZERO;

        for (CartItem item : cart.values()) {
            subTotal = subTotal.add(
                    priceCalculator.calculatePrice(
                            item.getProduct(),
                            item.getQuantity(),
                            productService.getMultiBuyOffer(item.getProduct().sku())
                    )
            );
        }

        // Apply bundle discounts
        BigDecimal bundleDiscounts = BigDecimal.ZERO;
        for (BundleOffer bundleOffer : productService.getBundleOffers()) {
            CartItem item1 = cart.get(bundleOffer.sku1());
            CartItem item2 = cart.get(bundleOffer.sku2());

            if (item1 != null && item2 != null) {
                int numberOfBundles = Math.min(item1.getQuantity(), item2.getQuantity());
                if (numberOfBundles > 0) {
                    BigDecimal totalDiscountForOffer = bundleOffer.discount().multiply(BigDecimal.valueOf(numberOfBundles));
                    bundleDiscounts = bundleDiscounts.add(totalDiscountForOffer);
                }
            }
        }

        return subTotal.subtract(bundleDiscounts).setScale(2, RoundingMode.HALF_UP);
    }

    public void clearCart() {
        cart.clear();
    }

    public Collection<CartItem> getCartItems() {
        return Collections.unmodifiableCollection(cart.values());
    }
}

