package com.interview.shoppingbasket;

import java.util.ArrayList;
import java.util.List;
public class PromotionsServiceBean implements PromotionsService{

    ArrayList<Promotion> availablePromotions = new ArrayList<>();
    @Override
    public List<Promotion> getPromotions(Basket basket) {


        Promotion promotion1 = new Promotion("product1", 0.5);
        Promotion promotion2 = new Promotion("product2", 0.1);
        Promotion promotion3 = new Promotion("productCode2", 2.0);

        availablePromotions.add(promotion1);
        availablePromotions.add(promotion2);
        availablePromotions.add(promotion3);

        ArrayList<Promotion> promotions = new ArrayList<>();

        for (BasketItem item : basket.getItems()){

            for (Promotion promotion: availablePromotions){
                if (promotion.getProductCode().equals(item.getProductCode()))
                    promotions.add(promotion);
            }
        }

        return promotions;
    }
}
