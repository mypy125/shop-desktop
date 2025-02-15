package com.mygitgor.model;

public class Stock {
    private String productCode;
    private Product product;
    private int quantity;

    public Stock(String productCode, int quantity) {
        this.productCode = productCode;
        this.quantity = quantity;
    }

    // Getters and Setters
    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}


