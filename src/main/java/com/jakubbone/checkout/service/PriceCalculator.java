package com.jakubbone.checkout.service;

import com.jakubbone.checkout.domain.MultiBuyOffer;
import com.jakubbone.checkout.domain.Product;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

@Component
public class PriceCalculator {

    public BigDecimal calculatePrice(Product product, int quantity, Optional<MultiBuyOffer> offerOpt){
        if(offerOpt.isEmpty()){
            product.price().multiply(BigDecimal.valueOf(quantity));
        }

        MultiBuyOffer offer = offerOpt.get();

        int multiBuyPackage = quantity / offer.requiredQuantity();
        int remainingItems = quantity % offer.requiredQuantity();

        BigDecimal multiBuyPrice = offer.specialPrice().multiply(BigDecimal.valueOf(multiBuyPackage));
        BigDecimal remainingItemsPrice = product.price().multiply(BigDecimal.valueOf(remainingItems));

        return multiBuyPrice.add(remainingItemsPrice).setScale(2, RoundingMode.HALF_UP);
    }
}
