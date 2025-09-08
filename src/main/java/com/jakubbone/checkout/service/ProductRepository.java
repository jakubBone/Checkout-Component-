package com.jakubbone.checkout.service;

import com.jakubbone.checkout.domain.BundleOffer;
import com.jakubbone.checkout.domain.MultiBuyOffer;
import com.jakubbone.checkout.domain.Product;
import com.jakubbone.checkout.exception.ProductNotFoundException;
import lombok.Getter;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@Getter
public class ProductRepository {
    private final Map<String, Product> products;
    private final Map<String, MultiBuyOffer> multiBuyOffers;
    private final List<BundleOffer> bundleOffers;

    public ProductRepository(Map<String, Product> products, Map<String, MultiBuyOffer> multiBuyOffers, List<BundleOffer> bundleOffers) {
        this.products = products;
        this.multiBuyOffers = multiBuyOffers;
        this.bundleOffers = bundleOffers;
    }

    public Product getProduct(String id) {
        Product product = products.get(id);
        if (product == null) {
            throw new ProductNotFoundException(id);
        }
        return product;
    }

    public Optional<MultiBuyOffer> getMultiBuyOffer(String productId) {
        return Optional.ofNullable(multiBuyOffers.get(productId));
    }
}
