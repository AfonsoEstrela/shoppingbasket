package com.interview.shoppingbasket;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

public class CheckoutPipelineTest {

    CheckoutPipeline checkoutPipeline;

    PricingService pricingService;

    PromotionsServiceBean promotionsService;

    @Mock
    Basket basket;

    @Mock
    CheckoutStep checkoutStep1;

    @Mock
    CheckoutStep checkoutStep2;

    @Mock
    CheckoutStep checkoutStep3;

    @BeforeEach
    void setup() {
        pricingService = Mockito.mock(PricingService.class);
        promotionsService = new PromotionsServiceBean();
        checkoutPipeline = new CheckoutPipeline();
    }

    @Test
    void returnZeroPaymentForEmptyPipeline() {
        PaymentSummary paymentSummary = checkoutPipeline.checkout(basket);

        assertEquals(paymentSummary.getRetailTotal(), 0.0);
    }

    @Test
    void executeAllPassedCheckoutSteps() {
        basket = new Basket();
        basket.add("product1", "myProduct", 10);
        basket.add("productCode2", "myProduct2", 12);
        basket.add("product2", "myProduct3", 10);
        basket.add("product1", "myProduct3", 14);
        basket.add("product2", "myProduct3", 20);
        basket.add("productCode2", "myProduct3", 11);

        when(pricingService.getPrice("product1")).thenReturn(3.99);
        when(pricingService.getPrice("product2")).thenReturn(2.0);
        when(pricingService.getPrice("productCode2")).thenReturn(1.5);

        checkoutStep1 = new BasketConsolidationCheckoutStep();
        checkoutStep2 = new PromotionCheckoutStep(promotionsService);
        checkoutStep3 = new RetailPriceCheckoutStep(pricingService);

        checkoutPipeline.addStep(checkoutStep1);
        checkoutPipeline.addStep(checkoutStep2);
        checkoutPipeline.addStep(checkoutStep3);

        PaymentSummary summary = checkoutPipeline.checkout(basket);

        assertEquals(summary.getRetailTotal(), 119.88);
        assertEquals(basket.getItems().size(),3);
        assertEquals(basket.getItems().get(0).getProductCode(), "product2");
        assertEquals(basket.getItems().get(0).getQuantity(), 30);
        assertEquals(basket.getItems().get(1).getProductCode(), "product1");
        assertEquals(basket.getItems().get(1).getQuantity(), 24);
        assertEquals(basket.getItems().get(2).getProductCode(), "productCode2");
        assertEquals(basket.getItems().get(2).getQuantity(), 23);

    }

}
