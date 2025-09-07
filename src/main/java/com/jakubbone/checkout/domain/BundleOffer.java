package com.jakubbone.checkout.domain;

import java.math.BigDecimal;

public record BundleOffer(String sku1, String sku2, BigDecimal discount){}
