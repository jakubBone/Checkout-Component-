package com.jakubbone.checkout;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

public class Receipt {
    Product product;
    int quantity;
    BigDecimal totalPrice
}
