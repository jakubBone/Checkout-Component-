package com.jakubbone.checkout.service;

import com.jakubbone.checkout.domain.MultiBuyOffer;
import com.jakubbone.checkout.domain.Product;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

@Component
public class PriceCalculator {

    public BigDecimal calculatePrice(Product product, int quantity, Optional<MultiBuyOffer> offerOpt) {
        if (offerOpt.isPresent() && quantity >= offerOpt.get().requiredQuantity()) {
            MultiBuyOffer offer = offerOpt.get();
            int offerQuantity = offer.requiredQuantity();

            int numberOfBundles = quantity / offerQuantity;
            int remainingItems = quantity % offerQuantity;

            BigDecimal bundlePrice = offer.specialPrice().multiply(BigDecimal.valueOf(numberOfBundles));
            BigDecimal remainingPrice = product.price().multiply(BigDecimal.valueOf(remainingItems));

            return bundlePrice.add(remainingPrice).setScale(2, RoundingMode.HALF_UP);
        }
        return product.price().multiply(BigDecimal.valueOf(quantity)).setScale(2, RoundingMode.HALF_UP);
    }
}
