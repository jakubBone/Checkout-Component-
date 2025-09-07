package com.jakubbone.checkout.domain;

import java.math.BigDecimal;

public record ReceiptItem(Product product, int quantity, BigDecimal unitPrice, BigDecimal totalPrice){}
