package com.jakubbone.checkout.service;

import com.jakubbone.checkout.domain.MultiBuyOffer;
import com.jakubbone.checkout.domain.Product;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;

@Component
public class PriceCalculator {

    public BigDecimal calculatePrice(Product product, int quantity, Optional<MultiBuyOffer> offer){
        if(offer.isEmpty()){
            product.price().multiply(BigDecimal.valueOf(quantity));
        }


    }
}
