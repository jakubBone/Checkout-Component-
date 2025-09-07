package com.jakubbone.checkout.config;

import com.jakubbone.checkout.domain.BundleOffer;
import com.jakubbone.checkout.domain.Product;
import com.jakubbone.checkout.domain.MultiBuyOffer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Configuration
public class ProductConfiguration {
    @Bean
    public Map<String, Product> products() {
        return Map.of(
                "A", new Product("A", new BigDecimal("40")),
                "B", new Product("B", new BigDecimal("10")),
                "C", new Product("C", new BigDecimal("30")),
                "D", new Product("D", new BigDecimal("25"))
        );
    }

    @Bean
    public Map<String, MultiBuyOffer> specialOffers(){
        return Map.of(
                "A", new MultiBuyOffer("A", 3, new BigDecimal("30")),
                "B", new MultiBuyOffer("B", 2, new BigDecimal("7.5")),
                "C", new MultiBuyOffer("C", 4, new BigDecimal("20")),
                "D", new MultiBuyOffer("D", 2, new BigDecimal("23.5"))
        );
    }

    /*@Bean
    public Map<String, BundleOffer> comboOffers() {
        return Map.of(
                "A/C", new BundleOffer("A", "C", new BigDecimal("-10")),
                "B/D", new BundleOffer("B", "D", new BigDecimal("-20"))
        );
    }*/

    @Bean
    public List<BundleOffer> bundleOffers() {
        return List.of(
                new BundleOffer("A", "B", new BigDecimal("5.00")),
                new BundleOffer("C", "D", new BigDecimal("10.00"))
        );
    }
}
