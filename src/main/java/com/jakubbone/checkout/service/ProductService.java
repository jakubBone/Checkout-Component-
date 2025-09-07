package com.jakubbone.checkout.service;

import com.jakubbone.checkout.domain.BundleOffer;
import com.jakubbone.checkout.domain.Product;
import com.jakubbone.checkout.domain.MultiBuyOffer;
import com.jakubbone.checkout.exception.ProductNotFoundException;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Getter
public class ProductService {
    private final Map<String, Product> products;
    private final Map<String, MultiBuyOffer> specialOffers;
    private final Map<String, BundleOffer> comboOffers;

    public ProductService(Map<String, Product> products, Map<String, MultiBuyOffer> specialOffers, Map<String, BundleOffer> comboOffers) {
        this.products = products;
        this.specialOffers = specialOffers;
        this.comboOffers = comboOffers;
    }

    public Product getProduct(String sku) {
        Product product = products.get(sku);
        if(product == null){
            throw new ProductNotFoundException(sku);
        }
        return product;
    }

    public MultiBuyOffer getSpecialOffer(String sku) {
        return specialOffers.get(sku);
    }

    public BundleOffer getComboOffer(String key) {
        return comboOffers.get(key);
    }
}
