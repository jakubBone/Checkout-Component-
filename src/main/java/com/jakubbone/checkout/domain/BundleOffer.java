package com.jakubbone.checkout.domain;

import java.math.BigDecimal;

public record BundleOffer(String firstProductId, String secondProductId, BigDecimal discount){}
