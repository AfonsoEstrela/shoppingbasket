package com.interview.shoppingbasket;

public class RetailPriceCheckoutStep implements CheckoutStep {
    private PricingService pricingService;
    private double retailTotal;

    public RetailPriceCheckoutStep(PricingService pricingService) {
        this.pricingService = pricingService;
    }

    @Override
    public void execute(CheckoutContext checkoutContext) {
        Basket basket = checkoutContext.getBasket();
        retailTotal = 0.0;

        for (BasketItem basketItem: basket.getItems()) {
            int quantity = basketItem.getQuantity();
            double price = pricingService.getPrice(basketItem.getProductCode());
            basketItem.setProductRetailPrice(price);
            if(basketItem.getPromotion() == null) {
                retailTotal += quantity * price;
            } else {
                retailTotal += applyPromotion(basketItem.getPromotion(), basketItem, basketItem.getProductRetailPrice());
            }
        }
        checkoutContext.setRetailPriceTotal(retailTotal);

    }

    public double applyPromotion(Promotion promotion, BasketItem item, double price) {
        /*
         * Implement applyPromotion method
         */

        double itemTotal = 0;

        if(promotion.getDiscount() > 1){
            int quantity = ((item.getQuantity() - item.getQuantity()%promotion.getDiscount().intValue())/promotion.getDiscount().intValue())
                    + item.getQuantity()%promotion.getDiscount().intValue();

            itemTotal = quantity*price;
        }else{
            price -= price*promotion.getDiscount();

            itemTotal = item.getQuantity()*price;
        }

        return itemTotal;
    }
}
