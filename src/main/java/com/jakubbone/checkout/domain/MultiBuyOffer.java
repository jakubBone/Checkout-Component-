package com.jakubbone.checkout.domain;

import java.math.BigDecimal;

public record MultiBuyOffer(String sku, int requiredQuantity, BigDecimal specialPrice) {}
