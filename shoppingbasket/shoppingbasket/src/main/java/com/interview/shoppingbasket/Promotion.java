package com.interview.shoppingbasket;

public class Promotion {
    private String productCode;

    private Double discount;

    public Promotion(String productCode, Double discount) {
        this.productCode = productCode;
        this.discount = discount;
    }

    public String getProductCode() {
        return productCode;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }
}
