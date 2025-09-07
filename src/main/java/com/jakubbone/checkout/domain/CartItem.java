package com.jakubbone.checkout.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CartItem {
    private Product product;
    private int quantity;

    public void addQuantity(int amount){
        this.quantity += amount;
    }
}
