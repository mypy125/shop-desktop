package com.mygitgor.model;

public class Stock {
    private String productCode;
    private Product product;
    private int quantity;

    public Stock() {
    }

    public Stock(String productCode, Product product, int quantity) {
        this.productCode = productCode;
        this.product = product;
        this.quantity = quantity;
    }

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
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        this.quantity = quantity;
    }
}


