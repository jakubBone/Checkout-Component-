package com.jakubbone.checkout;

import com.jakubbone.checkout.domain.Product;

import java.util.Map;

public interface Discount {
    double apply(Map<Product, Integer> basketPosition);
}
