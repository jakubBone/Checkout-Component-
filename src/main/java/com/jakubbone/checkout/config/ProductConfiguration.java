package com.jakubbone.checkout.config;

import com.jakubbone.checkout.domain.ComboOffer;
import com.jakubbone.checkout.domain.Product;
import com.jakubbone.checkout.domain.SpecialOffer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
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
    public Map<String, SpecialOffer> specialOffers(){
        return Map.of(
                "A", new SpecialOffer("A", 3, new BigDecimal("30")),
                "B", new SpecialOffer("B", 2, new BigDecimal("7.5")),
                "C", new SpecialOffer("C", 4, new BigDecimal("20")),
                "D", new SpecialOffer("D", 2, new BigDecimal("23.5"))
        );
    }

    @Bean
    public Map<String, ComboOffer> comboOffers() {
        return Map.of(
                "A/C", new ComboOffer("A", "C", new BigDecimal("-10")),
                "B/D", new ComboOffer("B", "D", new BigDecimal("-20"))
        );
    }
}
