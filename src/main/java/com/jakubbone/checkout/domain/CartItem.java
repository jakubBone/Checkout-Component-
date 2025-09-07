package com.jakubbone.checkout.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CartItem {
    private Product product;
    private int quantity;

    public void addQuantity(int amount){
        this.quantity += amount;
    }
}
