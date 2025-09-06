package com.jakubbone.checkout.service;

import com.jakubbone.checkout.domain.ComboOffer;
import com.jakubbone.checkout.domain.Product;
import com.jakubbone.checkout.domain.SpecialOffer;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Getter
public class ProductService {
    private final Map<String, Product> products;
    private final Map<String, SpecialOffer> specialOffers;
    private final Map<String, ComboOffer> comboOffers;

    public ProductService(Map<String, Product> products, Map<String, SpecialOffer> specialOffers, Map<String, ComboOffer> comboOffers) {
        this.products = products;
        this.specialOffers = specialOffers;
        this.comboOffers = comboOffers;
    }

    public Product getProduct(String sku){
        Product product = products.get(sku);
        if(product != null){
            return null;
        }
        return product;
    }
}
