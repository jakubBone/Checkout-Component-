package com.jakubbone.checkout.domain;

import java.math.BigDecimal;

public record AppliedDiscount(String description, BigDecimal amount) {}