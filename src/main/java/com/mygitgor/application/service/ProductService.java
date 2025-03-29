package com.mygitgor.application.service;

import com.mygitgor.domain.model.Product;

import java.util.List;

public interface ProductService {
    Product addProduct(Product product);
    Product updateProduct(Product product);
    void deleteProduct(String code);
    Product findByCode(String code);
    List<Product> findAll();
}
