package com.jakubbone.checkout.domain;

import java.math.BigDecimal;

public record SpecialOffer(String sku, int requiredQuantity, BigDecimal specialPrice) {}
