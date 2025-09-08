package com.jakubbone.checkout.domain;

import java.math.BigDecimal;

public record MultiBuyOffer(String productId, int requiredQuantity, BigDecimal specialPrice) {}
