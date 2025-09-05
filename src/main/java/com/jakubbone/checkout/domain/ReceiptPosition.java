package com.jakubbone.checkout.domain;

import java.math.BigDecimal;

public record ReceiptPosition(Product product, int quantity, BigDecimal totalPrice){}
