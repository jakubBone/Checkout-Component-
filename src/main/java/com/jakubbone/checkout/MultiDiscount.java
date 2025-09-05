package com.jakubbone.checkout;

import com.jakubbone.checkout.domain.Product;

import java.util.Map;

public class MultiDiscount implements Discount{
    @Override
    public double apply(Map<Product, Integer> basketPosition) {
        return 0;
    }
}
