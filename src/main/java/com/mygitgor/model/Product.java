package com.mygitgor.model;

import java.util.Date;
import java.util.Objects;

public class Product {
    private String code;
    private double price;
    private Date productionDate;
    private Date expirationDate;

    public Product() {
    }

    public Product(String code, double price, Date productionDate, Date expirationDate) {
        this.code = code;
        this.price = price;
        this.productionDate = productionDate;
        this.expirationDate = expirationDate;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(Date productionDate) {
        this.productionDate = productionDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Double.compare(price, product.price) == 0 && Objects.equals(code, product.code) && Objects.equals(productionDate, product.productionDate) && Objects.equals(expirationDate, product.expirationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, price, productionDate, expirationDate);
    }
}
