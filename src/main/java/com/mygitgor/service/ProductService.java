package com.mygitgor.service;

import com.mygitgor.model.Product;

import java.util.List;

public interface ProductService {
    void addProduct(Product product);
    void updateProduct(Product product);
    void deleteProduct(String code);
    Product findByCode(String code);
    List<Product> findAll();
}
