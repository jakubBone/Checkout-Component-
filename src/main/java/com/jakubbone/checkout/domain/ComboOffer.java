package com.jakubbone.checkout.domain;

import java.math.BigDecimal;

public record ComboOffer(String productSku1, String productSku2, BigDecimal discount){}
