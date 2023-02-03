package com.interview.shoppingbasket;

import java.util.ArrayList;
import java.util.List;

public class PromotionCheckoutStep implements CheckoutStep{

    private PromotionsService service;
    public PromotionCheckoutStep(PromotionsService service) {
        this.service = service;
    }

    @Override
    public void execute(CheckoutContext checkoutContext) {
        PromotionsServiceBean service = new PromotionsServiceBean();

        List<Promotion> promotions = service.getPromotions(checkoutContext.getBasket());

        for (Promotion promotion: promotions){

            for (BasketItem item: checkoutContext.getBasket().getItems()){
                if(promotion.getProductCode().equals(item.getProductCode())){
                        item.setPromotion(promotion);
                }
            }
        }
    }
}

