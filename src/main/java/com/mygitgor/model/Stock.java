package com.mygitgor.model;

import java.util.Objects;

public class Stock {
    private String productCode;
    private Product product;
    private int quantity;

    public Stock(){

    }
    public Stock(String productCode, int quantity) {
        this.productCode = productCode;
        this.quantity = quantity;
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
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Stock stock = (Stock) o;
        return quantity == stock.quantity && Objects.equals(productCode, stock.productCode) && Objects.equals(product, stock.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productCode, product, quantity);
    }
}


