package com.jakubbone.checkout.domain;

import java.math.BigDecimal;
import java.util.List;

public record Receipt(
    List<ReceiptItem> items,
    List<AppliedDiscount> discounts,
    BigDecimal finalTotal
) {}
