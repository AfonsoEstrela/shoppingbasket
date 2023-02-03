package com.interview.shoppingbasket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Basket {
    private List<BasketItem> items = new ArrayList<>();

    public void add(String productCode, String productName, int quantity) {
        BasketItem basketItem = new BasketItem();
        basketItem.setProductCode(productCode);
        basketItem.setProductName(productName);
        basketItem.setQuantity(quantity);

        items.add(basketItem);
    }

    public List<BasketItem> getItems() {
        return items;
    }

    public void consolidateItems() {
        HashMap<String, BasketItem> map = new HashMap<>();

        for(BasketItem item: items){
            BasketItem currentItem = new BasketItem();
            if(map.containsKey(item.getProductCode())) {
                currentItem = map.get(item.getProductCode());
                currentItem.setQuantity(currentItem.getQuantity() + item.getQuantity());
            }else{
                map.put(item.getProductCode(), item);
            }

        }

        items = new ArrayList<>(map.values());
    }
}
