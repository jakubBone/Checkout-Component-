package com.jakubbone.checkout.service.integraton;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureWebMvc
class CheckoutTest {

    @Autowired
    WebApplicationContext context;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void shouldApplyMultiBuyOffer() throws Exception {
        // 3A = special price 30 instead of 120
        mockMvc.perform(post("/api/checkout/scan/A"));
        mockMvc.perform(post("/api/checkout/scan/A"));
        mockMvc.perform(post("/api/checkout/scan/A"));

        mockMvc.perform(post("/api/checkout/checkout"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.finalTotal").value(30.00));
    }

    @Test
    void shouldApplyBundleDiscount() throws Exception {
        // A + B = 40 + 10 - 5 (bundle) = 45
        mockMvc.perform(post("/api/checkout/scan/A"));
        mockMvc.perform(post("/api/checkout/scan/B"));

        mockMvc.perform(post("/api/checkout/checkout"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.finalTotal").value(45.00))
                .andExpect(jsonPath("$.discounts.length()").value(1));
    }

    @Test
    void shouldCombineOffersCorrectly() throws Exception {
        // 3A (30) + 2B (7.5) with 2 bundle A+B (-5 * 2) = 27.5
        mockMvc.perform(post("/api/checkout/scan/A"));
        mockMvc.perform(post("/api/checkout/scan/A"));
        mockMvc.perform(post("/api/checkout/scan/A"));
        mockMvc.perform(post("/api/checkout/scan/B"));
        mockMvc.perform(post("/api/checkout/scan/B"));

        mockMvc.perform(post("/api/checkout/checkout"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.finalTotal").value(27.50));
    }

    @Test
    void shouldReturnNotFoundForInvalidSku() throws Exception {
        mockMvc.perform(post("/api/checkout/scan/Z"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldNotApplyMultiBuyOfferBelowRequiredQuantity() throws Exception {
        // 2A = 2 * 40 = 80. No multi-buy offer
        mockMvc.perform(post("/api/checkout/scan/A"));
        mockMvc.perform(post("/api/checkout/scan/A"));

        mockMvc.perform(post("/api/checkout/checkout"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.finalTotal").value(80.00))
                .andExpect(jsonPath("$.discounts.length()").value(0));
    }
}