package com.jakubbone.checkout;

import java.util.Map;

public interface Discount {
    double add(Map<Product, Integer> bucket);
}
