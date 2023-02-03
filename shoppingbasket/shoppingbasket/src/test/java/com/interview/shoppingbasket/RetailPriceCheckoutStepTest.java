package com.interview.shoppingbasket;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class RetailPriceCheckoutStepTest {

    PricingService pricingService;

    PromotionsServiceBean promotionsService;
    CheckoutContext checkoutContext;
    Basket basket;

    @BeforeEach
    void setup() {
        pricingService = Mockito.mock(PricingService.class);
        promotionsService = new PromotionsServiceBean();
        basket = new Basket();
        checkoutContext = new CheckoutContext(basket);
    }

    @Test
    void setPriceZeroForEmptyBasket() {

        RetailPriceCheckoutStep retailPriceCheckoutStep = new RetailPriceCheckoutStep(pricingService);

        retailPriceCheckoutStep.execute(checkoutContext);

        Mockito.verify(checkoutContext).setRetailPriceTotal(0.0);
    }

    @Test
    void setPriceOfProductToBasketItem() {

        basket.add("product1", "myproduct1", 10);
        basket.add("product2", "myproduct2", 10);

        PromotionCheckoutStep promotionCheckoutStep = new PromotionCheckoutStep(promotionsService);

        promotionCheckoutStep.execute(checkoutContext);

        assertEquals(checkoutContext.getBasket().getItems().get(0).getProductCode(),"product1");
        assertEquals(checkoutContext.getBasket().getItems().get(1).getProductCode(), "product2");
        assertEquals(checkoutContext.getBasket().getItems().get(0).getPromotion().getDiscount(), 0.5);
        assertEquals(checkoutContext.getBasket().getItems().get(1).getPromotion().getDiscount(), 0.1);

        when(pricingService.getPrice("product1")).thenReturn(3.99);
        when(pricingService.getPrice("product2")).thenReturn(2.0);
        RetailPriceCheckoutStep retailPriceCheckoutStep = new RetailPriceCheckoutStep(pricingService);

        retailPriceCheckoutStep.execute(checkoutContext);

        assertEquals(checkoutContext.paymentSummary().getRetailTotal(), 37.95);


    }

}
